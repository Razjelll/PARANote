package com.parabits.paranote.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parabits.paranote.R;
import com.parabits.paranote.data.models.Label;

import java.util.List;

/**
 * Created by Razjelll on 28.08.2017.
 */

public class LabelAdapter extends ArrayAdapter{

    private int m_resource;
    private Context m_context;
    private List<Label> m_items;

    public LabelAdapter(@NonNull Context context, @LayoutRes int resource, List<Label> data) {
        super(context, resource, data);
        m_resource = resource;
        m_context = context;
        m_items = data;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        final ViewHolder viewHolder;
        View rowView = view;
        if(rowView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(m_context);
            rowView = inflater.inflate(m_resource, null);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)rowView.getTag();
        }

        Label label = m_items.get(position);
        viewHolder.setNameText(label.getName());
        viewHolder.check(label.isChecked());

        return rowView;
    }

    public void add(Label label)
    {
        m_items.add(0, label); //TODO zrobiono wstawianie na pierwszym miejscu. Zajrzeć czy tak może być
        notifyDataSetChanged();
    }

    public void changeChecked(int position)
    {
        Label label = m_items.get(position);
        label.setChecked(!label.isChecked());
        notifyDataSetChanged();
    }

    private class ViewHolder
    {
        private EditText m_name_edit_text;
        private CheckBox m_check_box;

        ViewHolder(View view)
        {
            m_name_edit_text = view.findViewById(R.id.name_edit_text);
            m_check_box = view.findViewById(R.id.check_box);
        }

        public void setNameText(String text)
        {
            m_name_edit_text.setText(text);
        }

        public void check(boolean checked)
        {
            m_check_box.setChecked(checked);
        }

        public void changeChecked()
        {
            m_check_box.setChecked(!m_check_box.isChecked());
        }
    }
}
