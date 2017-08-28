package com.parabits.paranote.data.database;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.parabits.paranote.data.database.tables.NotesTable;

import java.util.ArrayList;

public class NotesProvider extends ContentProvider {

    public enum Table
    {
        NOTES,
        LABELS,
        REMINDERS
    }

    static final String PROVIDER_NAME = "com.parabits.paranote.NotesProvider";
    static final String NOTES_URL = "content://" + PROVIDER_NAME + "/note";

    private static final int SINGLE_NOTE = 10;
    private static final int ALL_NOTES = 20;

    private final String[] ALL_COLUMNS = {"*"};

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "note", ALL_NOTES);
        uriMatcher.addURI(PROVIDER_NAME, "note/#", SINGLE_NOTE);
    }

    public DbOpenHelper openHelper;

    @Override
    public boolean onCreate() {
        openHelper = new DbOpenHelper(getContext()); //TODO to może będzie można usunąć
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        projection = projection == null ? ALL_COLUMNS : projection;
        //TODO można tutaj jeszcze dorobić sortorder i reszte
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        SQLiteDatabase database = openHelper.getReadableDatabase();
        queryBuilder.setTables(NotesTable.TABLE_NAME);
        switch (uriMatcher.match(uri))
        {
            case ALL_NOTES:
                return database.query(NotesTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case SINGLE_NOTE:
                selection = selection == null ? NotesTable.ID_COLUMN +"=?" :
                        NotesTable.ID_COLUMN +"=? AND (" + selection +")";
                String noteID = uri.getLastPathSegment();
                selectionArgs = fixSelectionArgs(selectionArgs, noteID);
                return database.query(NotesTable.TABLE_NAME, projection, selection, selectionArgs, null,null, sortOrder);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }


    private String[] fixSelectionArgs(String[] selectionArgs, String id)
    {
        if(selectionArgs == null)
        {
            selectionArgs = new String[]{id};
        } else {
            String[] newSelectionArgs = new String[selectionArgs.length + 1];
            newSelectionArgs[0] = id;
            System.arraycopy(selectionArgs, 0, newSelectionArgs, 1, selectionArgs.length);
        }
        //TODO zobaczyć czy newSelectionArgs nie trzeba zwrócić
        return selectionArgs;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        /*long rowID = openHelper.getWritableDatabase().insert(NotesTable.TABLE_NAME, "", contentValues);
        if(rowID > 0)
        {
            Uri resultUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(resultUri, null);
            return resultUri;
        }*/
        SQLiteDatabase database = openHelper.getWritableDatabase();
        Uri result = doInsert(uri, contentValues, database);
        return result;
    }

    private Uri doInsert(Uri uri, ContentValues values, SQLiteDatabase database)
    {
        Uri resultUri = null;
        switch (uriMatcher.match(uri)){
            case ALL_NOTES:
                long id = database.insert(NotesTable.TABLE_NAME, "", values);
                if(id==-1) throw new SQLException("Insertion data error");
                resultUri = Uri.withAppendedPath(uri, String.valueOf(id));
        }
        return resultUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] contentValues)
    {
        SQLiteDatabase database = openHelper.getWritableDatabase();
        int count = 0;
        try{
            database.beginTransaction();
            Uri resultUri = null;
            for(ContentValues values : contentValues)
            {
                resultUri = doInsert(uri, values, database);
                if(resultUri != null) {
                    count++;
                } else {
                    count = 0;
                    throw new SQLException("Bulk insert error");
                }
            }
            database.setTransactionSuccessful();
        }finally {
            database.endTransaction();
        }
        return count;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase database = openHelper.getWritableDatabase();
        switch (uriMatcher.match(uri))
        {
            case ALL_NOTES:
                count = database.delete(NotesTable.TABLE_NAME,selection, selectionArgs); break;
            case SINGLE_NOTE:
                String id = uri.getPathSegments().get(1);
                selection = selection == null ? NotesTable.ID_COLUMN +"=?" :
                        NotesTable.ID_COLUMN +"=? AND (" + selection +")";
                count = database.delete(NotesTable.TABLE_NAME, selection, selectionArgs); break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null); //TODO zobaczyć do czego to służy
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;
        SQLiteDatabase database = openHelper.getWritableDatabase();
        switch (uriMatcher.match(uri))
        {
            case ALL_NOTES:
                count = database.update(NotesTable.TABLE_NAME, contentValues, selection, selectionArgs); break;
            case SINGLE_NOTE:
                String id = uri.getPathSegments().get(1);
                selection = selection == null ? NotesTable.ID_COLUMN +"=?" :
                        NotesTable.ID_COLUMN +"=? AND (" + selection +")";
                selectionArgs = fixSelectionArgs(selectionArgs, id);
                count  = database.update(NotesTable.TABLE_NAME, contentValues, selection,selectionArgs); break;
            default:
                throw new IllegalArgumentException("Unknow URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null); //TODO zobaczyć do czego to służy
        return count;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException
    {
        SQLiteDatabase database = openHelper.getWritableDatabase();
        ContentProviderResult[] result = new ContentProviderResult[operations.size()];
        try{
            database.beginTransaction();
            for(int i=0; i<operations.size(); i++)
            {
                ContentProviderOperation operation = operations.get(i);
                result[i] = operation.apply(this, result, i);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();;
        }
        return result;
    }

    public static Uri getUri(Table table)
    {
        String url = getUrl(table);
        return Uri.parse(url);
    }

    public static Uri getUri(Table table, long id)
    {
        String url = getUrl(table) + "/" + String.valueOf(id);
        return Uri.parse(url);
    }

    private static String getUrl(Table table)
    {
        switch (table)
        {
            case NOTES:
                return NOTES_URL;
            //TODO dokończyć to
            default:
                throw new IllegalArgumentException("Unknown table");
        }
    }

}
