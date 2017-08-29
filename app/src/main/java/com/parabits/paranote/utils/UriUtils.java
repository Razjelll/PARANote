package com.parabits.paranote.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


public class UriUtils {

    public static String getImagePath(Uri uri, Context context)
    {
        String[] projection = {MediaStore.Images.Media.DATA};
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = {uri.getLastPathSegment()};
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filepath = "";
        if(cursor.moveToFirst())
        {
            filepath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filepath;
    }
}
