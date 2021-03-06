package com.parabits.paranote.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.parabits.paranote.utils.SizeUtils;

import java.util.List;

public class ParaToolbar extends LinearLayout{

    private final int BUTTON_SIZE = 48;

    private LinearLayout m_toolbar_layout;
    private List<ImageButton> m_constants_buttons;
    private LinearLayout m_context_buttons;
    private ImageView m_menu_button;


    private ToolbarMenuView m_menu_list_view;

    private boolean m_menu_open;

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
        if(getResources().getConfiguration().orientation == VERTICAL)
        {
            verticalInit();
        } else {
            horizontalInit();
        }
    }

    private void verticalInit()
    {
        //ustawienie orientacji
        this.setOrientation(VERTICAL);

        setupEmptyClickableLayout(VERTICAL);
        setupMenu(VERTICAL);
        setupToolbarLayout(HORIZONTAL); //pasek narzędzi ma odwrotną orientację niż układ zawierający pasek i menu
    }

    private void horizontalInit()
    {
        this.setOrientation(HORIZONTAL);

        setupContextLayout(VERTICAL, this); // pasek przycisków kontekstowych ułożony jest wertykalnie
        setupEmptyClickableLayout(HORIZONTAL);
        setupMenu(HORIZONTAL);
        setupToolbarLayout(VERTICAL); // pasek narzędzi także ma orientaję odwrotna do widoku
    }

    private void setupEmptyClickableLayout(int orientation)
    {
        View view = new View(getContext());
        if(orientation == VERTICAL)
        {
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.f));
        } else { //HORIZONTAL
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.f);
            params.gravity = Gravity.BOTTOM;
            view.setLayoutParams(params);
        }
        view.setFocusable(false);
        this.addView(view);
    }

    private void setupToolbarLayout(int toolbarOrientation)
    {
        // ustawianie układu w którym będą znajdować się wszystkie przyciski
        m_toolbar_layout = new LinearLayout(getContext());
        m_toolbar_layout.setOrientation(toolbarOrientation);
        m_toolbar_layout.setBackgroundColor(Color.WHITE);
        m_toolbar_layout.bringToFront();
        this.addView(m_toolbar_layout);

        if(toolbarOrientation == HORIZONTAL)
        {
            m_toolbar_layout.addView(getSeparator(toolbarOrientation)); //separator
            setupContextLayout(toolbarOrientation, m_toolbar_layout); // menu przycisków kontekstowych
            m_toolbar_layout.addView(getSeparator(toolbarOrientation)); //separator
            setupMenuButton(); //przycisk menu
        } else {
            m_toolbar_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            setupMenuButton();
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return false;
    }

    private View getSeparator(int orientation)
    {
        final int SEPARATOR_WIDTH = 1;
        View separator;
        separator = new View(getContext());
        separator.setBackgroundColor(Color.GRAY);
        if(orientation == HORIZONTAL)
        {
            separator.setLayoutParams(new ViewGroup.LayoutParams(SEPARATOR_WIDTH, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SEPARATOR_WIDTH));
        }
        return separator;
    }

    private void setupMenu(int orientation)
    {
        m_menu_list_view = new ToolbarMenuView(getContext(), orientation==VERTICAL);
        m_menu_list_view.setBackgroundColor(Color.WHITE);
        m_menu_list_view.setVisibility(GONE);
        m_menu_list_view.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        m_menu_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //m_menu_adapter.getItem(i).getListener().onClick(null); //TODO przetestować to dokładnie
                m_menu_list_view.getItem(i).getListener().onClick(null); //TODO zobaczyć właściwie co się tutaj dzieje
                m_menu_list_view.hide();
            }
        });

        this.addView(m_menu_list_view);
    }

    private void setupContextLayout(int orientation, ViewGroup parent)
    {
        // utowrzenie układu przewijającego. W zależności od orientacji ekranu tworzony jest
        // układ przewijany w dół lub w bok
        m_context_buttons = new LinearLayout(getContext());
        FrameLayout scrollView;
        LinearLayout.LayoutParams scrollParams;
        if(orientation == HORIZONTAL)
        {
            scrollView = new HorizontalScrollView(getContext());
            m_context_buttons.setOrientation(HORIZONTAL);
            scrollParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.f);
        } else { // VERTICAL
            ScrollView verticalScrollView = new ScrollView(getContext());
            verticalScrollView.setFillViewport(true);
            scrollView = verticalScrollView;
            m_context_buttons.setOrientation(LinearLayout.VERTICAL);
            m_context_buttons.setBackgroundColor(Color.WHITE);
            scrollParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        }
        scrollView.setLayoutParams(scrollParams);
        scrollView.addView(m_context_buttons);
        parent.addView(scrollView);
    }

    private void setupMenuButton()
    {
        // dodanie przycisku menu
        ToolbarButton menuButton = new ToolbarButton(getContext());
        menuButton.setImageResource(android.R.drawable.ic_menu_agenda);

        menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //spinner.performClick();
                if(m_menu_list_view.isShowed())
                {
                    m_menu_list_view.hide();
                } else {
                    m_menu_list_view.show();
                }
            }
        });
        m_toolbar_layout.addView(menuButton);
    }

    public void addConstantsButton(int imageResource, String name, OnClickListener listener, int index) //TODO index można ustawić w innym miejscu ponieważ jest mało wygodne
    {
        ToolbarButton imageButton = getImageButton(imageResource,name, listener);
        m_toolbar_layout.addView(imageButton, index);
        m_constants_buttons_count++;
    }
    public void addConstantsButton(int position,final int imageResource,final String name,final ToolbarMenu menu)
    {

        ToolbarButton toolbarButton = getImageButton(imageResource, name, getListener(menu));
        m_toolbar_layout.addView(toolbarButton, position);
        m_constants_buttons_count++;
    }

    private OnClickListener getListener(final ToolbarMenu menu)
    {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO sprawdzić jakie menu zostało ostanio otwierane

                m_menu_list_view.setItems(menu.getItems());
                m_menu_list_view.show();
            }
        };

        return listener;
    }

    private ToolbarButton getImageButton(int imageResource, String name, OnClickListener listener)
    {
        ToolbarButton imageButton = new ToolbarButton(getContext());
        int buttonSize = (int)SizeUtils.getPx(BUTTON_SIZE, getContext());
        imageButton.setLayoutParams(new ViewGroup.LayoutParams(buttonSize, buttonSize));
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

    public void addContextButton(int position, int imageResource, String name, final ToolbarMenu menu)
    {
        ToolbarButton toolbarMenu = getImageButton(imageResource, name, getListener(menu));
        m_context_buttons.addView(toolbarMenu);
    }

    public void setContextAction(ToolbarMenu menu)
    {
        m_context_buttons.removeAllViews();
        ToolbarButton toolbarButton;
        ToolbarMenuItem item;
        for(int i=0; i<menu.getItems().size(); i++)
        {
            item = menu.getItems().get(i);
            toolbarButton = getImageButton(item.getResource(), item.getText(), item.getListener());
            m_context_buttons.addView(toolbarButton);
        }
        this.requestLayout();
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        Rect rect = new Rect(m_menu_list_view.getLeft(), m_menu_list_view.getTop(), m_menu_list_view.getRight(), m_menu_list_view.getBottom());
        if(!rect.contains(Math.round(getX() + event.getX()), Math.round(getY() + event.getY())))
        {
            if(m_menu_list_view.isShowed())
            {
                m_menu_list_view.hide();
            }
            //TODO tutaj jeszcze sprawdzić, czy nie naciśnięto na przycisk odpowiadający za menu
        }
        return super.dispatchTouchEvent(event);
    }

}



