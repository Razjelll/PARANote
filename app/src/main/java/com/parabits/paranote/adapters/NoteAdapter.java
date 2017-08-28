package com.parabits.paranote.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parabits.paranote.R;
import com.parabits.paranote.data.models.Note;

import java.util.List;

/**
 * Created by Razjelll on 27.08.2017.
 */

public class NoteAdapter extends ArrayAdapter
{

    private int m_resource;
    private Context m_context;
    private List<Note> m_items;

    public NoteAdapter(@NonNull Context context, @LayoutRes int resource, List<Note> data) {
        super(context, resource, data);
        m_resource= resource;
        m_context = context;
        m_items = data;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        ViewHolder viewHolder;
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
        Note note = m_items.get(position);
        viewHolder.setTitleText(note.getTitle());

        return rowView;
    }

    private class ViewHolder
    {
        private TextView m_title_text_view;
        // TODO pododawać resztę pól

        ViewHolder(View view)
        {
            m_title_text_view = (TextView) view.findViewById(R.id.title_text_view);
        }

        void setTitleText(String text)
        {
            m_title_text_view.setText(text);
        }
    }
}
