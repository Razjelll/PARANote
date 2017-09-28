package com.parabits.paranote.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parabits.paranote.R;
import com.parabits.paranote.adapters.NoteAdapter;
import com.parabits.paranote.data.models.NoteElement;
import com.parabits.paranote.views.NoteListView;


/**
 * A placeholder fragment containing a simple view.
 */
public class NoteActivityFragment extends Fragment {

    private EditText m_title_edit_text;
    private RecyclerView m_note_view;
    private Button m_add_text_button;
    private Button m_add_image_button;
    private Button m_add_list_button;
    private NoteAdapter m_note_adapter;

    public NoteActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        m_title_edit_text = view.findViewById(R.id.title_edit_text);
        m_note_view = view.findViewById(R.id.note_recycler_view);
        m_add_text_button = view.findViewById(R.id.add_text_button);
        m_add_text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteElement element = new NoteElement();
                element.setType(NoteElement.TEXT);
                element.setContent("");
                m_note_adapter.addElement(element);
                m_note_adapter.notifyItemInserted(m_note_adapter.getItemCount());
            }
        });
        m_add_image_button = view.findViewById(R.id.add_image_button);
        m_add_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT <= 19)
                {
                    Intent intent  = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, 10);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 10);
                }
            }
        });
        m_add_list_button = view.findViewById(R.id.add_list_button);
        m_add_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteElement element = new NoteElement();
                element.setType(NoteElement.LIST);
                element.setContent("");
                m_note_adapter.addElement(element);
                m_note_adapter.notifyItemInserted(m_note_adapter.getItemCount());

            }
        });

        m_note_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        m_note_adapter = new NoteAdapter();
        m_note_view.setAdapter(m_note_adapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == 10)
            {
                Uri selectedImageUri = data.getData();
                NoteElement element = new NoteElement();
                element.setType(NoteElement.IMAGE);
                element.setContent(selectedImageUri.toString());
                m_note_adapter.addElement(element);
            }
        }
    }
}
