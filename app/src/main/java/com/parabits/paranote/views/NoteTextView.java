package com.parabits.paranote.views;

import android.content.Context;
import android.view.MotionEvent;


public class NoteTextView extends android.support.v7.widget.AppCompatEditText implements INoteElementView {

    public NoteTextView(Context context) {
        super(context);
    }

    @Override
    public Type getType() {
        return Type.TEXT;
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

}
