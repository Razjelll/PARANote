package com.parabits.paranote.data.models;


import android.database.Cursor;

import com.parabits.paranote.data.database.tables.NotesTable;

public class NoteCreator implements ModelCreator<Note>{

    public  Note createFromCursor(Cursor cursor)
    {
        long id = cursor.getLong(NotesTable.ID_POSITION);
        String title = cursor.getString(NotesTable.TITLE_POSITION);
        String content = cursor.getString(NotesTable.CONTENT_POSITION);
        Date creationDate = new Date(cursor.getInt(NotesTable.CREATING_DATE_POSITION));
        Date updateDate = new Date(cursor.getInt(NotesTable.UPDATE_DATE_POSITION));

        Note note = new Note();
        note.setID(id);
        note.setTitle(title);
        note.setContent(content);
        note.setCreationDate(creationDate);
        note.setUpdateDate(updateDate);

        return note;
    }
}
