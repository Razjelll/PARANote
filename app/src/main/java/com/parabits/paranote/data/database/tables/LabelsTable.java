package com.parabits.paranote.data.database.tables;

import static com.parabits.paranote.data.database.tables.TableConstants.*;
public class LabelsTable {

    public static final String TABLE_NAME = "labels";

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";

    public static final int ID_POSITION = 0;
    public static final int NAME_POSITION = 1;

    public static final int COLUMN_COUNT = 2;

    public static String getCreateStatement()
    {
        return CREATE_TABLE + TABLE_NAME + "(" +
                ID_COLUMN + INT + PK + AUTOINCREMENT + "," +
                NAME_COLUMN + TEXT + NOT_NULL +UNIQUE +
                ")";
    }
}
