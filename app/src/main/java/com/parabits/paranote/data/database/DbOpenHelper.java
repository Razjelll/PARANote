package com.parabits.paranote.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.parabits.paranote.data.database.tables.LabelsTable;
import com.parabits.paranote.data.database.tables.NoteLabelsTable;
import com.parabits.paranote.data.database.tables.NotesTable;

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ParaNote.db";

    public DbOpenHelper(Context context)
    {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(NotesTable.getCreateStatement()); //utworzenie tabeli notatek
        sqLiteDatabase.execSQL(LabelsTable.getCreateStatement());
        sqLiteDatabase.execSQL(NoteLabelsTable.getCreateStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
