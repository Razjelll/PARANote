package com.parabits.paranote.data.storage;

import android.content.Context;

import java.io.File;
import java.io.IOException;

/**
 * Created by Razjelll on 30.08.2017.
 */

public abstract class Storage {

    public File saveFile(String filename, String catalog, byte[] data, Context context) throws IOException
    {
        return FileSystem.savedFile(data, getPath(filename, catalog, context));
    }

    protected abstract String getPath(String filename, String catalog, Context context);

    public boolean deleteFile(String filename, String catalog, Context context)
    {
        return FileSystem.deleteFile(getPath(filename, catalog, context));
    }

    public boolean deleteDirectory(String directoryPath, Context context)
    {
        return FileSystem.deleteDirectory(getPath(directoryPath, context));
    }

    public abstract String getPath(String catalog, Context context);

    public File getDirectory(String catalog, Context context)
    {
        return FileSystem.getFile(getPath(catalog, context));
    }

    public File saveFileInCache(String filename, byte[] data, Context context) throws IOException
    {
        return FileSystem.savedFile(data, getCachePath(filename, context));
    }

    public abstract String getCachePath(String filename, Context context);

    public File getFileFromCache(String filename, Context context)
    {
        return FileSystem.getFile(getCachePath(filename, context));
    }

    public boolean deleteFileFromCache(String filename, Context context)
    {
        return FileSystem.deleteFile(getCachePath(filename, context));
    }


}
