package com.parabits.paranote.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.parabits.paranote.R;

/**
 * Created by Razjelll on 11.09.2017.
 */
//TODO dokończyć to
public class SlideButton extends android.support.v7.widget.AppCompatSeekBar {

    private static final int HORIZONTAL_ORIENTATION = 0;
    private static final int VERTICAL_ORIENTATION = 1;

    private Drawable m_thumb;
    private SlideButtonListener m_listener;
    private int m_orientation;

    public interface SlideButtonListener
    {
        void onRight();
    }

    public SlideButton(Context context) {
        super(context);
    }

    public SlideButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SlideButton(Context context, AttributeSet attrs, int style)
    {
        super(context, attrs, style);

    }

    private void init()
    {

    }
}
