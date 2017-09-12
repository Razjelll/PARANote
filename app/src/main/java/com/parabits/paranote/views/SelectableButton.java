package com.parabits.paranote.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.parabits.paranote.R;

public class SelectableButton extends android.support.v7.widget.AppCompatButton
{

    private boolean m_selected;

    public SelectableButton(Context context) {
        super(context);
        init();
    }

    public SelectableButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SelectableButton(Context context, AttributeSet attrs, int resId)
    {
        super(context, attrs, resId);
        init();
    }

    private void init()
    {
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onChange(View view) {
//                click();
//            }
//        });
        setBackgroundResource(R.drawable.selector_selectable_button);
        setAllCaps(false);

    }

    public void click()
    {
        m_selected = !m_selected;
        setSelected(m_selected);
    }

    @Override
    public boolean isSelected()
    {
        return m_selected;
    }

}
