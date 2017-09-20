package com.parabits.paranote.views;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parabits.paranote.R;

import java.util.ArrayList;
import java.util.List;

public class NoteListView extends ListView implements INoteElementView {

    public class NoteListItem
    {
        private boolean m_selected;
        private String m_text;

        NoteListItem(boolean selected, String text)
        {
            m_selected = selected;
            m_text = text;
        }

        public boolean isSelected(){
            return m_selected;}
        public String getText(){return m_text;}

        public void setSelected(boolean selected){m_selected =selected;}
        public void setText(String text){m_text = text;}
    }

    //private ListView m_list_view;
    //private Button m_add_element_button;
    private NoteListAdapter m_adapter;

    public NoteListView(Context context) {
        super(context);
        init();
    }

    public NoteListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public NoteListView(Context context, AttributeSet attrs, int style)
    {
        super(context, attrs, style);
        init();
    }

    public NoteListItem getItem(int position)
    {

        return m_adapter.getItem(position);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {

        //if(!hasFocus()) requestFocus();
        performItemClick(this, 0, 0);
        return false;
    }

    private void init()
    {
        List<NoteListItem> items = new ArrayList<>();
        //items.add(new NoteListItem(false, ""));
        m_adapter = new NoteListAdapter(getContext(), R.layout.item_list_element, items);
        setAdapter(m_adapter);
        addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                setListViewHeightBasedChildren();
            }

            @Override
            public void onViewDetachedFromWindow(View view) {

            }
        });
    }

    public int getItemsCount()
    {
        return m_adapter.getCount();
    }


    private void setListViewHeightBasedChildren()
    {
        // TODO nie wykonywać tego jeżeli nie dołączono jescze widoku
        ListAdapter adapter = getAdapter();
        if(adapter == null)
        {
            return;
        }

        int totalHeight = 0;
        for(int i=0; i<adapter.getCount(); i++)
        {
            View listItem = adapter.getView(i, null, this);
            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = getLayoutParams();
        // sprawdzamy czy widok ma parametry
        // jeżeli widok nie jest dołączony do inego widoku params będzie null. Taka sytuacja następuje
        // w przypadku ustawiania widoku z bazy danych. Najpierw ustawiany jest widok i dołączane są
        // wszystkie elementy, a następnie dołączany jest widok
        if(params != null)
        {
            params.height = totalHeight + (getDividerHeight() * (adapter.getCount() -1));
            setLayoutParams(params);
            requestLayout();
        }

    }

    @Override
    public Type getType() {
        return Type.LIST;
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

    public void addItem()
    {
        m_adapter.addItem();
    }

    public void addItem(boolean selected, String text)
    {
        m_adapter.addItem(selected, text);
    }

    public void removeSelectedItem()
    {
        //int selectedItemPosition = m_list_view.getSelectedItemPosition();
        int selectedItemPosition = getSelectedItemPosition();
        m_adapter.removeItem(selectedItemPosition);
    }

    private class NoteListAdapter extends ArrayAdapter
    {

        private Context m_context;
        private List<NoteListItem> m_items;
        private int m_resource;

        public NoteListAdapter(@NonNull Context context, @LayoutRes int resource, List<NoteListItem> data) {
            super(context, resource, data);
            m_context = context;
            m_items = data;
            m_resource = resource;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup)
        {
            View rowView = view;
            final ViewHolder viewHolder;
            if(rowView == null)
            {
                LayoutInflater inflater = LayoutInflater.from(m_context);
                rowView = inflater.inflate(m_resource, null);
                viewHolder = new ViewHolder(rowView);
                viewHolder.setFocusChangeListener(new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if(!b) //jeżeli pole straci focus
                        {
                            m_items.get(position).setText(viewHolder.getText());
                        }
                    }
                });
                viewHolder.setOnClickListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        m_items.get(position).setSelected(b);
                    }
                });
                rowView.setTag(viewHolder);
                rowView.setFocusable(false);

            }
            else
            {
                viewHolder = (ViewHolder) rowView.getTag();
            }

            NoteListItem element = m_items.get(position);
            viewHolder.setChecked(element.isSelected());
            viewHolder.setText(element.getText());

            return rowView;
        }

        public void setChecked(boolean selected, int position)
        {
            m_items.get(position).setSelected(selected);
            notifyDataSetChanged();
        }

        public void setText(String text, int position)
        {
            m_items.get(position).setText(text);
            notifyDataSetChanged();
        }

        @Override
        public NoteListItem getItem(int position)
        {
            return m_items.get(position);
        }

        public void addItem()
        {
            addItem(false, "");
        }

        public void addItem(boolean selected, String text)
        {
            m_items.add(new NoteListItem(selected, text));
            notifyDataSetChanged();
            setListViewHeightBasedChildren();
        }

        public void removeItem(int position)
        {
            m_items.remove(position);
            notifyDataSetChanged();
            setListViewHeightBasedChildren();
        }

        private class ViewHolder
        {
            private CheckBox m_check_box;
            private EditText m_edit_text;

            public ViewHolder(View view)
            {
                m_check_box = view.findViewById(R.id.check_box);
                m_edit_text = view.findViewById(R.id.content_text_view);
                m_edit_text.setFocusable(true);
                m_edit_text.setFocusableInTouchMode(true);
            }

            public void setChecked(boolean selected)
            {
                m_check_box.setChecked(selected);
            }

            boolean isChecked() {
                return m_check_box.isChecked();
            }

            public void setText(String text)
            {
                m_edit_text.setText(text);
            }

            public String getText() { return m_edit_text.getText().toString();}


            void setFocusChangeListener(OnFocusChangeListener listener)
            {
                m_edit_text.setOnFocusChangeListener(listener);
            }

            void setOnClickListener(CompoundButton.OnCheckedChangeListener listener)
            {
                m_check_box.setOnCheckedChangeListener(listener);
            }

        }
    }
}
