package com.parabits.paranote.views;

import android.content.Context;
import android.widget.ImageButton;


public class ToolbarButton extends android.support.v7.widget.AppCompatImageButton{

    private String m_name;
    public ToolbarButton(Context context) {
        super(context);
    }

    public String getName() { return m_name;}
    public void setName(String name) { m_name = name;}
}
