package com.parabits.paranote.data.database.tables;

import  static com.parabits.paranote.data.database.tables.TableConstants.*;
public class RemindersTable {

    public static final String TABLE_NAME = "reminders";

    public static final String ID = "word_fk";
    public static final String DATE = "date";
    public static final String ACTIVE = "active";
    public static final String MONTHLY = "monthly";
    public static final String PATTERN = "pattern";
    public static final String END_DATE = "end_date";

    public static final int ID_POSITION = 0;
    public static final int DATE_POSITION = 1;
    public static final int ACTIVE_POSITION = 2;
    public static final int MONTHLY_POSITION = 3;
    public static final int PATTERN_POSITION = 4;
    public static final int END_DATE_POSITION = 5;

    public static final int COLUMNS_COUNT = 6;

    public static String getCreateStatement()
    {
        return CREATE_TABLE + TABLE_NAME + "(" +
                ID + INT + PK  + ", " +
                DATE + INT + NOT_NULL + ", " +
                ACTIVE + INT + NOT_NULL + DEFAULT + "1" + "," +
                MONTHLY + INT + NULL + ", " +
                PATTERN + TEXT + NULL + "," +
                END_DATE + INT + NULL + "," +
                FK + "(" + ID +")" + REFERENCES + NotesTable.TABLE_NAME + "(" + NotesTable.ID_COLUMN + ")" +
                ")";
    }
}
