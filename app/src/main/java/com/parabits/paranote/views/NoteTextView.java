package com.parabits.paranote.views;

import android.content.Context;
import android.widget.EditText;


public class NoteTextView extends android.support.v7.widget.AppCompatEditText implements INoteView {

    public NoteTextView(Context context) {
        super(context);
    }

    @Override
    public Type getType() {
        return Type.TEXT;
    }
}
