package com.parabits.paranote.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.parabits.paranote.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter dla NoteListView
 */

public class NoteListViewAdapter extends RecyclerView.Adapter<NoteListViewAdapter.ListItemViewHolder>{

    /** Lista elementów, która zostanie wyświetlona w widoku listy, do której został dołączony adapter */
    private List<NoteListItem> m_items;

    private Context m_context;

    public NoteListViewAdapter()
    {
        m_items = new ArrayList<>();
        m_items.add(new NoteListItem(false, "Ahoj"));
    }

    public void addElement(NoteListItem item)
    {
        assert m_items != null;
        m_items.add(item);
        notifyItemInserted(m_items.size()-1);
    }

    public void removeElement(int position)
    {
        assert m_items != null;
        m_items.remove(position);
        notifyItemRemoved(position);
    }

    public NoteListItem getItem(int position)
    {
        assert m_items != null;
        if(position < m_items.size())
        {
            return m_items.get(position);
        }
        return null; // elementu nie znaleziono
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        m_context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(m_context);
        View view = inflater.inflate(R.layout.item_note_list_item, parent, false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        holder.bindData(m_items.get(position));
    }

    @Override
    public int getItemCount() {
        return m_items.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder{

        /** Przestrzeń, za pomocą której będzie można przeciągnąć element listy w górę albo w dół. */
        private ImageView m_item_holder;
        private CheckBox m_check_box;
        private EditText m_text_view;

        ListItemViewHolder(View itemView) {
            super(itemView);
            m_item_holder = itemView.findViewById(R.id.item_holder);
            m_check_box = itemView.findViewById(R.id.check_box);
            m_text_view = itemView.findViewById(R.id.content_text_view);
        }

        private void addListeners()
        {
            //TODO dodać słuchaczy

        }

        void bindData(NoteListItem item)
        {
            m_check_box.setChecked(item.isChecked());
            m_text_view.setText(item.getText());
        }
    }
}
