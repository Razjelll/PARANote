package com.parabits.paranote.views;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

public class NoteImageView extends android.support.v7.widget.AppCompatImageView implements INoteView {

    private Uri m_image_uri;

    public NoteImageView(Context context) {
        super(context);
    }

    @Override
    public Type getType() {
        return Type.IMAGE;
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
}
