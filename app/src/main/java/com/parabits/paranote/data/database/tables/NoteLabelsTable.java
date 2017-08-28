package com.parabits.paranote.data.database.tables;

import static com.parabits.paranote.data.database.tables.TableConstants.*;

public class NoteLabelsTable {

    public static final String TABLE_NAME = "note_labels";

    public static final String NOTE_COLUMN = "note_fk";
    public static final String LABEL_COLUMN = "label_fk";

    public static final int NOTE_POSITION = 0;
    public static final int LABEL_POSITION = 1;

    public static final int COLUMNS_COUNT = 2;

    public static String getCreateStatement()
    {
        return CREATE_TABLE + TABLE_NAME + "(" +
                NOTE_COLUMN + INT + NOT_NULL + "," +
                LABEL_COLUMN + INT + NOT_NULL + "," +
                FK +"(" + NOTE_COLUMN +")" + REFERENCES + NotesTable.TABLE_NAME + "(" + NotesTable.ID_COLUMN + ")," +
                FK + "(" + LABEL_COLUMN + ")" + REFERENCES + LabelsTable.TABLE_NAME + "(" + LabelsTable.ID_COLUMN + ")" +
                ")";
    }
}
