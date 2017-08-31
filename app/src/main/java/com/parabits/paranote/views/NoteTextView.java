package com.parabits.paranote.views;

import android.content.Context;


public class NoteTextView extends android.support.v7.widget.AppCompatEditText implements INoteElementView {

    public NoteTextView(Context context) {
        super(context);
    }

    @Override
    public Type getType() {
        return Type.TEXT;
    }
}
