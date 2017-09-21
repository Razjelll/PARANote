package com.parabits.paranote.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parabits.paranote.R;
import com.parabits.paranote.data.models.Note;

import java.util.Collections;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>
{
    public interface OnItemClickListener
    {
        void onClick(NoteItem note);
        void onLongClick(NoteItem note);
    }

    private OnItemClickListener m_on_click_listener;
    private List<NoteItem> m_items;

    public void setOnClickListener(OnItemClickListener listener)
    {
        m_on_click_listener = listener;
    }

    public NoteAdapter(List<NoteItem> items, RecyclerView recyclerView)
    {
        m_items = items;
        prepareItemTouchHelper(recyclerView);
    }

    private void prepareItemTouchHelper(RecyclerView recyclerView)
    {
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(m_items,viewHolder.getAdapterPosition(), target.getAdapterPosition());
                notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, final int position) {
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_on_click_listener != null)
                {
                    m_on_click_listener.onClick(m_items.get(position));
                }
            }
        });
        holder.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(m_on_click_listener != null)
                {
                    m_on_click_listener.onLongClick(m_items.get(position));
                    return true;
                }
                return false;
            }
        });
        holder.bindNote(m_items.get(position));

    }

    @Override
    public int getItemCount() {
        return m_items.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder
    {
        private View m_view; //widok calego elementu, potrzebny, aby dodać do niego listenery
        private TextView m_title_text_view;
        private TextView m_creation_date_text_view;

        public NoteViewHolder(View itemView) {
            super(itemView);
            m_view = itemView;
            m_title_text_view = itemView.findViewById(R.id.title_text_view);
            m_creation_date_text_view = itemView.findViewById(R.id.create_date_text_view);
        }

        public void setTitle(String title){
            m_title_text_view.setText(title);
        }

        public void setCreationDate(String creationDate)
        {
            m_creation_date_text_view.setText(creationDate);
        }

        public void bindNote(NoteItem note)
        {
            m_title_text_view.setText(note.getTitle());
            m_creation_date_text_view.setText(note.getCreationDate());
        }

        View getView() {return m_view;}
    }
}




