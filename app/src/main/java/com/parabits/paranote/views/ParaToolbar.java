package com.parabits.paranote.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.List;

public class ParaToolbar extends LinearLayout{

    private List<ImageButton> m_constants_buttons;
    private LinearLayout m_context_buttons;
    private ImageView m_menu_button;

    private int m_constants_buttons_count;

    public ParaToolbar(Context context) {
        super(context);
        init();
    }

    public ParaToolbar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ParaToolbar(Context context, AttributeSet attrs, int resId)
    {
        super(context, attrs, resId);
        init();
    }

    private void init()
    {
        // dodanie separatora oddzielającego przyciski stałe od przycisków kontekstowych
        View separator1 = new View(getContext());
        separator1.setLayoutParams(new ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
        separator1.setBackgroundColor(Color.GRAY);
        this.addView(separator1);

        // utowrzenie układu przewijającego. W zależności od orientacji ekranu tworzony jest
        // układ przewijany w dół lub w bok
        m_context_buttons = new LinearLayout(getContext());
        FrameLayout scrollView;
        LinearLayout.LayoutParams scrollParams;
        if(getOrientation() == HORIZONTAL)
        {
            scrollView = new HorizontalScrollView(getContext());
            m_context_buttons.setOrientation(HORIZONTAL);
            scrollParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.f);
        } else { // VERTICAL
            scrollView = new ScrollView(getContext());
            m_context_buttons.setOrientation(VERTICAL);
            scrollParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.f);
        }
        scrollView.setLayoutParams(scrollParams);
        scrollView.addView(m_context_buttons);
        this.addView(scrollView);

        // dodanie separatora odzielającego przyciski kontekstowe od przycisku menu
        View separator2 = new View(getContext());
        separator2.setLayoutParams(new ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
        separator2.setBackgroundColor(Color.GRAY);
        this.addView(separator2);

        // dodanie przycisku menu
        ToolbarButton menuButton = new ToolbarButton(getContext());
        menuButton.setImageResource(android.R.drawable.ic_menu_agenda);

        this.addView(menuButton);
    }



    public void addConstantsButton(int imageResource, String name, OnClickListener listener, int index)
    {

        ToolbarButton imageButton = getImageButton(imageResource,name, listener);
        this.addView(imageButton, index);
        m_constants_buttons_count++;
    }

    private ToolbarButton getImageButton(int imageResource, String name, OnClickListener listener)
    {
        ToolbarButton imageButton = new ToolbarButton(getContext());
        imageButton.setImageResource(imageResource);
        imageButton.setName(name);
        imageButton.setOnClickListener(listener);



        return imageButton;
    }

    public void addContextButton(int imageResource, String name, OnClickListener listener)
    {
        ToolbarButton imageButton = getImageButton(imageResource, name, listener);
        m_context_buttons.addView(imageButton);
    }

    public void clearContextLayout()
    {
        m_context_buttons.removeAllViews();
    }

    public void removeConstantsButton(int position)
    {
        if(position < m_constants_buttons_count)
        {
            this.removeViewAt(position);
            m_constants_buttons_count--;
        }
    }


}
