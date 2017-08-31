package com.parabits.paranote.data.storage;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Created by Razjelll on 30.08.2017.
 */

public class StorageSystem {

    private static Storage getStorage() {
        //TODO tutaj dorobić sprawdzanie, z jakiej pamięci powinno się korzystać i zwracanie odpowiedzniej
        //TODO zobaczyć czy da radę ograniczyć liczbę alokacji nowych obiektów, np poprzez utworzenie zmiennej lokalne lokalnej Storage
        return new InternalStorage();
    }

    public static Uri getUriFromCache(String filename, Context context)
    {
        File file = getStorage().getFileFromCache(filename, context);
        if(file.exists())
        {
            return Uri.fromFile(file);
        }
        return null;
    }

    public boolean deleteFromCache(String filename, Context context)
    {
        return getStorage().deleteFileFromCache(filename, context);
    }

    public static File saveFileInCache(String filename, byte[] data, Context context) throws IOException {
        return getStorage().saveFileInCache(filename, data, context);
    }

}
