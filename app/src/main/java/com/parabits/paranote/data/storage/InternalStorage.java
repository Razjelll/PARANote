package com.parabits.paranote.data.storage;

import android.content.Context;

/**
 * Created by Razjelll on 30.08.2017.
 */

public class InternalStorage extends Storage {
    @Override
    protected String getPath(String filename, String catalog, Context context) {
        return context.getFilesDir() + "/" + catalog + "/" + filename;
     }

    @Override
    public String getPath(String catalog, Context context) {
        return context.getFilesDir() + "/" + catalog;
    }

    @Override
    public String getCachePath(String filename, Context context) {
        return context.getCacheDir() + "/" + filename;
    }
}
