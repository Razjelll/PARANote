package com.parabits.paranote.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.parabits.paranote.R;
import com.parabits.paranote.adapters.LabelAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class LabelActivityFragment extends Fragment {

    private EditText m_search_edit_text;
    private ListView m_list_view;
    private ViewGroup m_add_label_container;
    private ViewGroup m_toolbar;

    private ImageButton m_edit_button;
    private ImageButton m_add_button;

    private ImageButton m_adding_cancel_button;
    private EditText m_adding_edit_text;
    private ImageButton m_save_label_button;

    public LabelActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_label, container, false);
        m_search_edit_text = view.findViewById(R.id.search_edit_text);
        m_list_view = view.findViewById(R.id.list);
        m_add_label_container =  view.findViewById(R.id.add_label_container);
        m_toolbar = view.findViewById(R.id.toolbar);
        m_edit_button = m_toolbar.findViewById(R.id.edit_button);
        m_add_button = m_toolbar.findViewById(R.id.add_button);

        m_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO zobaczyć czy da radę zapobiec alokacji
                if(m_add_label_container.getVisibility() == View.VISIBLE)
                {
                    m_add_label_container.setVisibility(View.GONE);

                } else {
                    showAddLabelContainer(m_add_label_container);
                }
            }
        });

        m_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO zrobić dodawanie
            }
        });

        //TODO pobranie etykiet z bazy danych
        return view;
    }

    private void showAddLabelContainer(View view)
    {
        if(m_adding_edit_text == null)
        {
            setupAddContainer(view);
        }
        m_add_label_container.setVisibility(View.VISIBLE);
    }

    private void setupAddContainer(View view)
    {
        m_adding_cancel_button = view.findViewById(R.id.cancel_button);
        m_adding_edit_text = view.findViewById(R.id.add_edit_text);
        m_save_label_button = view.findViewById(R.id.save_button);

        m_adding_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_add_label_container.setVisibility(View.GONE);
            }
        });

        m_save_label_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLabel(m_adding_edit_text.getText().toString());
            }
        });
    }
    private void saveLabel(String name)
    {

    }
}
