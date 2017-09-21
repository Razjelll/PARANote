package com.parabits.paranote.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parabits.paranote.R;
import com.parabits.paranote.adapters.NoteAdapter;
import com.parabits.paranote.adapters.NoteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private RecyclerView m_recycler_view;
    private LinearLayoutManager m_linear_layout_manager;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_main, container, false);
        m_recycler_view = view.findViewById(R.id.recycler_view);
        m_linear_layout_manager = new LinearLayoutManager(getActivity());
        m_recycler_view.setLayoutManager(m_linear_layout_manager);

        List<NoteItem> items = new ArrayList<>();
        NoteItem item1 = new NoteItem();
        item1.setTitle("Pierwsza notatka");
        item1.setCreationDate("10.10.2010");
        NoteItem item2 = new NoteItem();
        item2.setTitle("Druga notatka");
        item2.setCreationDate("12.09.2017");
        items.add(item1);
        items.add(item2);

        NoteAdapter adapter = new NoteAdapter(items, m_recycler_view);
        adapter.setOnClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onClick(NoteItem note) {
                Toast.makeText(getActivity(), "Kliknięto na item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(NoteItem note) {
                Toast.makeText(getActivity(), "Długo kliknięto na item", Toast.LENGTH_SHORT).show();
            }
        });
        m_recycler_view.setAdapter(adapter);

        return view;
    }


}
