package com.parabits.paranote.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.parabits.paranote.R;
import com.parabits.paranote.adapters.NoteAdapter;
import com.parabits.paranote.data.database.NoteDao;
import com.parabits.paranote.data.models.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView m_notes_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button tempButton = (Button)findViewById(R.id.temp_button);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditNoteActivity();
            }
        });

        m_notes_list_view = (ListView) findViewById(R.id.list);
        List<Note> notesList = new NoteDao(this).getAll();
        NoteAdapter adapter = new NoteAdapter(this, R.layout.item_note, notesList);
        m_notes_list_view.setAdapter(adapter);
    }

    private void startEditNoteActivity()
    {
        Intent intent = new Intent(this, EditNoteActivity.class);
        //Intent intent = new Intent(this, LabelActivity.class);
        startActivity(intent);
    }
}

