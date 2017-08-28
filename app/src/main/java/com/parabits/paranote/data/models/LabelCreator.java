package com.parabits.paranote.data.models;

import android.database.Cursor;

import com.parabits.paranote.data.database.tables.LabelsTable;

public class LabelCreator implements ModelCreator<Label>{

    @Override
    public Label createFromCursor(Cursor cursor) {
        String name = cursor.getString(LabelsTable.NAME_POSITION);
        Label label = new Label();
        label.setName(name);
        return label;
    }
}
