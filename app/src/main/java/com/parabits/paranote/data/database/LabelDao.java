package com.parabits.paranote.data.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.parabits.paranote.data.database.tables.LabelsTable;
import com.parabits.paranote.data.models.Label;
import com.parabits.paranote.data.models.LabelCreator;

import java.util.ArrayList;
import java.util.List;


public class LabelDao {

    private ContentResolver m_content_resolver;

    public LabelDao(Context context)
    {
        m_content_resolver = context.getContentResolver();
    }

    public void add(Label label)
    {
        ContentValues values = new ContentValues();
        values.put(LabelsTable.NAME_COLUMN, label.getName());
        Uri uri = NotesProvider.getUri(NotesProvider.Table.LABELS);
        m_content_resolver.insert(uri, values);
    }

    public boolean delete(long id)
    {
        if(id > 0)
        {
            String selection = LabelsTable.ID_COLUMN + "=?";
            String[] selectionArgs = {String.valueOf(id)};
            Uri uri = NotesProvider.getUri(NotesProvider.Table.LABELS, id);
            int deletedRows = m_content_resolver.delete(uri, selection, selectionArgs);
            return deletedRows > 0;
        }
        return false;
    }

    public Label get(long id)
    {

        if(id > 0)
        {
            String[] columns = {"*"};
            Uri uri = NotesProvider.getUri(NotesProvider.Table.NOTES, id);
            Cursor cursor = m_content_resolver.query(uri, columns, null, null, null);
            if(cursor.moveToFirst())
            {
                LabelCreator creator = new LabelCreator();
                return creator.createFromCursor(cursor);
            }
        }
        return null;
    }

    public List<Label> getAll()
    {
        List<Label> labelsList = new ArrayList<>();
        String[] columns = {"*"};
        Uri uri = NotesProvider.getUri(NotesProvider.Table.NOTES);
        Cursor cursor = m_content_resolver.query(uri, columns, null, null, null);
        if(cursor.moveToFirst())
        {
            Label label = null;
            LabelCreator creator = new LabelCreator();
            do {
                label = creator.createFromCursor(cursor);
                if(label != null)
                {
                    labelsList.add(label);
                }
            } while(cursor.moveToNext());
        }
        return labelsList;
    }
}
