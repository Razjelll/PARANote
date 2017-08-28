package com.parabits.paranote.data.database.tables;

import static com.parabits.paranote.data.database.tables.TableConstants.*;

public class NotesTable {

    public static final String TABLE_NAME = "notes";

    public static final String ID_COLUMN = "id";
    public static final String TITLE_COLUMN = "title";
    public static final String CONTENT_COLUMN = "content";
    public static final String CREATING_DATE_COLUMN = "creating_date";
    public static final String UPDATE_DATE_COLUMN = "update_date";

    public static final int ID_POSITION = 0;
    public static final int TITLE_POSITION = 1;
    public static final int CONTENT_POSITION = 2;
    public static final int CREATING_DATE_POSITION = 3;
    public static final int UPDATE_DATE_POSITION = 4;

    public static final int COLUMNS_COUNT = 5;

    public static String getCreateStatement()
    {
        return CREATE_TABLE + TABLE_NAME + "(" +
                ID_COLUMN + INT + PK + AUTOINCREMENT + "," +
                TITLE_COLUMN + TEXT + NOT_NULL + "," +
                CONTENT_COLUMN + TEXT + NOT_NULL + "," +
                CREATING_DATE_COLUMN + INT + NOT_NULL + "," +
                UPDATE_DATE_COLUMN + INT + NOT_NULL +
                ")";
    }
}
