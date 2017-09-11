package com.parabits.paranote.data.database;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.parabits.paranote.data.models.IModelCreator;

public abstract class BaseDao<T> {

    private ContentResolver m_content_resolver;
    private String m_content_url;
    private IModelCreator m_model_creator;
    private NotesProvider.Table m_table;
    private String m_id_column;

    public BaseDao(Context context, String contentUrl, IModelCreator modelCreator, NotesProvider.Table table, String idColumn)
    {
        m_content_resolver = context.getContentResolver();
        m_content_url = contentUrl;
        m_model_creator = modelCreator;
        m_table = table;
        m_id_column = idColumn;
    }

    public abstract void add(T model);

    public boolean delete(long id)
    {
        if(id > 0)
        {
            String selection = m_id_column + "=?";
            String[] selectionArgs = {String.valueOf(id)};
            Uri uri = NotesProvider.getUri(m_table, id);
            //TODO dokończyć to

        }
        return false;
    }


}
