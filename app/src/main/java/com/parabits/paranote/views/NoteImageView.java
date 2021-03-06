package com.parabits.paranote.views;

import android.content.Context;
import android.net.Uri;

public class NoteImageView extends android.support.v7.widget.AppCompatImageView implements INoteElementView {

    private Uri m_image_uri;

    public NoteImageView(Context context) {
        super(context);
        setFocusable(true);
    }

    @Override
    public Type getType() {
        return Type.IMAGE;
    }

    @Override
    public int getId()
    {
        return super.getId();
    }

    @Override
    public void setId(int id)
    {
        super.setId(id);
    }

    @Override
    public void setImageURI(Uri uri)
    {
        m_image_uri = uri;
        super.setImageURI(uri);
    }

    public Uri getImageUri()
    {
        return m_image_uri;
    }
    public void setUri(Uri uri) {m_image_uri = uri;}
}
