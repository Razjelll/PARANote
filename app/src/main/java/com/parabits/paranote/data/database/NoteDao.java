package com.parabits.paranote.data.database;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.parabits.paranote.data.models.Note;
import com.parabits.paranote.data.models.NoteCreator;
import com.parabits.paranote.data.database.tables.NotesTable;

import java.util.ArrayList;
import java.util.List;

public class NoteDao {

    private ContentResolver m_content_resolver;


    public NoteDao(Context context)
    {
        m_content_resolver = context.getContentResolver();
    }

    public long add(Note note)
    {
        //TODO tutaj zrobić jakąś klasę, która będzie tworzyłą ContentValues z modelu
        ContentValues values = new ContentValues();
        values.put(NotesTable.TITLE_COLUMN, note.getTitle());
        values.put(NotesTable.CONTENT_COLUMN, note.getContent());
        values.put(NotesTable.CREATING_DATE_COLUMN, note.getCreationDate().getCode());
        values.put(NotesTable.UPDATE_DATE_COLUMN, note.getUpdateDate().getCode());
        Uri uri = NotesProvider.getUri(NotesProvider.Table.NOTES);
        Uri resultUri = m_content_resolver.insert(uri,values);
        if(uri != null)
        {
            return Long.parseLong(resultUri.getLastPathSegment());
        }
        return -1;
        //return resultUri != null;
    }

    public boolean delete(long id)
    {
        if(id > 0)
        {
            Uri uri = NotesProvider.getUri(NotesProvider.Table.NOTES, id);
            int deletedRows = m_content_resolver.delete(uri, null, null);
            return deletedRows > 0;
        }
        return false;
    }

    public Note get(long id)
    {
        if(id > 0)
        {
            String[] columns = {"*"};
            Uri uri = NotesProvider.getUri(NotesProvider.Table.NOTES, id);
            Cursor cursor = m_content_resolver.query(uri, columns, null, null, null);
            if(cursor.moveToFirst())
            {
                NoteCreator creator = new NoteCreator();
                return creator.createFromCursor(cursor);
            }
        }
        return null;
    }

    public List<Note> getAll(){
        List<Note> notesList = new ArrayList<>();
        String[] columns = {"*"};
        Uri uri = NotesProvider.getUri(NotesProvider.Table.NOTES);
        Cursor cursor = m_content_resolver.query(uri, columns, null, null, null);
        if(cursor.moveToFirst())
        {
            Note note = null;
            NoteCreator creator = new NoteCreator();
            do {
                note = creator.createFromCursor(cursor);
                if(note != null)
                {
                    notesList.add(note);
                }
            } while(cursor.moveToNext());
        }
        return notesList;
    }
}
