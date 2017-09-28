package com.parabits.paranote.views;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.parabits.paranote.adapters.NoteListItem;
import com.parabits.paranote.adapters.NoteListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoteListView extends RecyclerView implements INoteElementView {

    private NoteListViewAdapter m_adapter;

    public NoteListView(Context context) {
        super(context);
    }

    public NoteListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public NoteListView(Context context, AttributeSet attrs, int style)
    {
        super(context, attrs, style);
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public void setAdapter(Adapter adapter)
    {
        m_adapter = (NoteListViewAdapter) adapter;
    }

    public void addItem(NoteListItem item){
        m_adapter.addElement(item);
    }

    /** Dodanie elementu bez zaznaczenia i bez tekstu do listy */
    public void addItem() { m_adapter.addElement(new NoteListItem(false, ""));}
}
