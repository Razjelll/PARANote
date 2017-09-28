package com.parabits.paranote.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parabits.paranote.R;
import com.parabits.paranote.data.models.NoteElement;
import com.parabits.paranote.utils.BitmapUtils;
import com.parabits.paranote.views.NoteListView;
import com.parabits.paranote.views.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter  extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    private List<NoteElement> m_items;
    private Context m_context;

    public NoteAdapter()
    {
        m_items = new ArrayList<>();
    }

    public void addElement(NoteElement element)
    {
        m_items.add(element);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position)
    {
        return m_items.get(position).getType();
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        m_context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType)
        {
            case NoteElement.TEXT:
                view = inflater.inflate(R.layout.item_text_element, parent, false);
                return new TextViewHolder(view);

            case NoteElement.IMAGE:
                view = inflater.inflate(R.layout.item_image_element, parent, false);
                return new ImageViewHolder(view);
            case NoteElement.LIST:
                view = inflater.inflate(R.layout.item_list_element, parent,false);
                return new ListViewHolder(view);
            default:
                throw new IllegalArgumentException("Incorrect note element type");
        }
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        //TODO dodać słuchaczy
        holder.bindData(m_items.get(position));
    }

    @Override
    public int getItemCount() {
        return m_items.size();
    }

    public abstract class NoteViewHolder extends RecyclerView.ViewHolder
    {
        public NoteViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindData(NoteElement element);
    }

    private class TextViewHolder extends NoteViewHolder
    {
        private EditText m_edit_text;

        public TextViewHolder(View itemView) {
            super(itemView);
            m_edit_text = itemView.findViewById(R.id.edit_text);
        }

        public void bindData(NoteElement element)
        {
            m_edit_text.setText(element.getContent());
        }
    }

    private class ImageViewHolder extends NoteViewHolder
    {
        private PhotoView m_image_view;
        private Bitmap m_last_bitmap;

        public ImageViewHolder(View itemView)
        {
            super(itemView);
            m_image_view = itemView.findViewById(R.id.image_view);
            m_image_view.setResolutionWidth(300);
            m_image_view.setResolutionHeight(400);
        }

        public void bindData(NoteElement element)
        {
            m_image_view.loadImage(element.getContent());
        }
    }

    private class ListViewHolder extends NoteViewHolder
    {
        private NoteListView m_list_view;
        private NoteListViewAdapter m_adapter;

        ListViewHolder(View itemView)
        {
            super(itemView);
            m_list_view = itemView.findViewById(R.id.list);
            m_adapter = new NoteListViewAdapter();
            m_list_view.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            m_list_view.setHasFixedSize(true);
            m_list_view.setAdapter(m_adapter);

            // przy tworzeniu listy od razu dodajemy pusty element, aby lista był widoczna i użytkownik nie musiał podejmować
            // żadnych kroków, aby mieć dostęp do listy
            m_list_view.addItem();
        }

        @Override
        public void bindData(NoteElement element) {

        }
    }
}
