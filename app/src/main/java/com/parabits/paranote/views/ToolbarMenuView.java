package com.parabits.paranote.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parabits.paranote.R;

import java.util.List;


public class ToolbarMenuView extends ListView {

    private final int ANIMATION_DURATION = 250;

    private ToolbarMenuAdapter m_adapter;

    private Animation m_enter_animation;
    private Animation m_exit_animation;

    private boolean m_showed;

    private boolean m_vertical_orientation;

    public ToolbarMenuView(Context context, boolean verticalOrientation) {
        super(context);
        m_vertical_orientation = verticalOrientation;
        if(m_vertical_orientation) //jeżeli orientacja rodzica jest wertykalna animacje będą od góry i od doły
        {
            m_enter_animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
            m_exit_animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);


        } else { //jeżeli orientacja jest horyzontalna animacja będzie w prawo i w lewo
            m_enter_animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_left);
            m_exit_animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_right);
            setLayoutParams(new LinearLayoutCompat.LayoutParams(getHalfScreenHorizontalSize(), ViewGroup.LayoutParams.MATCH_PARENT));
        }
        m_enter_animation.setDuration(ANIMATION_DURATION);
        m_exit_animation.setDuration(ANIMATION_DURATION);
    }

    private int getHalfScreenHorizontalSize()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.widthPixels /2;
    }

    public void setItems(List<ToolbarMenuItem> items)
    {
        m_adapter = new ToolbarMenuAdapter(getContext(), android.R.layout.simple_spinner_item, items);
        setAdapter(m_adapter);
    }

    public ToolbarMenuItem getItem(int position)
    {
        return m_adapter.getItem(position);
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
        //TODO zobaczyć co się tutaj robi
        return super.onTouchEvent(event);
    }

    class ToolbarMenuAdapter extends ArrayAdapter
    {
        private Context m_context;
        private List<ToolbarMenuItem> m_items;

        public ToolbarMenuAdapter(@NonNull Context context, @LayoutRes int resource, List<ToolbarMenuItem> data) {
            super(context, resource, data);
            m_context = context;
            m_items = data;
        }


        @Override
        public View getView(int position, View view, ViewGroup viewGroup)
        {
            View rowView = view;
            ViewHolder viewHolder;
            if(rowView == null)
            {
                LinearLayout layout = new LinearLayout(getContext());
                layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                layout.setGravity(Gravity.CENTER_VERTICAL);
                ImageButton imageButton = new ImageButton(getContext());
                imageButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageButton.setClickable(false);
                imageButton.setFocusable(false);
                imageButton.setFocusableInTouchMode(false);
                TextView textView = new TextView(getContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setClickable(false);
                textView.setFocusable(false);
                textView.setFocusableInTouchMode(false);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.f));

                layout.addView(imageButton);
                layout.addView(textView);
                viewHolder = new ViewHolder();
                viewHolder.setImageButton(imageButton);
                viewHolder.setTextView(textView);
                rowView = layout;

                rowView.setTag(viewHolder);
            } else
            {
                viewHolder = (ViewHolder) rowView.getTag();
            }
            ToolbarMenuItem item = m_items.get(position);
            //rowView.setOnClickListener(item.getListener());
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Kliknięto item", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.setImage(item.getResource());
            viewHolder.setText(item.getText());

            return rowView;
        }

        @Override
        public ToolbarMenuItem getItem(int position)
        {
            return m_items.get(position);
        }

        private class ViewHolder
        {
            private ImageView m_image_button;
            private TextView m_text_view;


            public void setImageButton(ImageButton imageButton)
            {
                m_image_button = imageButton;
                m_image_button.setClickable(false);
            }

            public void setTextView(TextView textView)
            {
                m_text_view = textView;
            }

            public void setImage(int resource)
            {
                m_image_button.setImageResource(resource);
            }

            public void setText(String text)
            {
                m_text_view.setText(text);
            }
        }
    }



}
