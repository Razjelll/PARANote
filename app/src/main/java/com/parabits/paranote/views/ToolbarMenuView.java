package com.parabits.paranote.views;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.parabits.paranote.R;


public class ToolbarMenuView extends ListView {

    private Animation m_enter_animation;
    private Animation m_exit_animation;

    private boolean m_showed;

    public ToolbarMenuView(Context context) {
        super(context);
        m_enter_animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        m_exit_animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
    }

    public void show()
    {
        setVisibility(VISIBLE);
        startAnimation(m_enter_animation);
        m_showed = true;
    }

    public void hide()
    {
        startAnimation(m_exit_animation);
        setVisibility(GONE);
        m_showed = false;
    }

    public boolean isShowed()
    {
        return m_showed;
    }

    @Override
    public boolean dispatchTouchEvent (MotionEvent event)
    {
        /*Rect rect = new Rect(getLeft(), getTop(), getRight(), getBottom());
        if(!rect.contains(Math.round(getX() + event.getX()), Math.round(getY() + event.getY())))
        {
            Toast.makeText(getContext(), "Kliknięto poza", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Kliknięto na listę", Toast.LENGTH_SHORT).show();
        }*/

        /*if(event.getAction() == MotionEvent.ACTION_OUTSIDE)
        {
            Toast.makeText(getContext(), "Kliknięto poza", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Kliknięto na listę", Toast.LENGTH_SHORT).show();
        }*/
        /*if(event.getAction() == MotionEvent.ACTION_CANCEL)
        {
            Toast.makeText(getContext(), "Kliknięto poza", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Kliknięto na listę", Toast.LENGTH_SHORT).show();
        }*/
        return super.onTouchEvent(event);
    }
}
