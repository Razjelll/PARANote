package com.parabits.paranote.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class BitmapUtils {

    public static Bitmap resize(Bitmap orginalBitmap, int newWidth, int newHeight)
    {
        return Bitmap.createScaledBitmap(orginalBitmap, newWidth, newHeight, true);
    }

    public static Bitmap getBitmap(Context context, Uri uri)
    {
        Bitmap bitmap = null;

        InputStream imageStream;
        try
        {
            imageStream = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(imageStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
