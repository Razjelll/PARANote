package com.parabits.paranote.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.parabits.paranote.R;
import com.parabits.paranote.adapters.NoteAdapter;
import com.parabits.paranote.data.database.NoteDao;
import com.parabits.paranote.data.models.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView m_notes_list_view;
    private NoteAdapter m_note_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button tempButton = (Button)findViewById(R.id.temp_button);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditNoteActivity(null);
            }
        });

        //TODO przenieść wszystko co jest związane z ładowaniem i wyświetlaniem listy do onResume, aby po poworcie z inej aktywności było zaktualizowane
        m_notes_list_view = (ListView) findViewById(R.id.list);
        List<Note> notesList = new NoteDao(this).getAll();
        m_note_adapter = new NoteAdapter(this, R.layout.item_note, notesList);
        m_notes_list_view.setAdapter(m_note_adapter);

        m_notes_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = m_note_adapter.getItem(i);
                startEditNoteActivity(note);
            }
        });
    }

    private void startEditNoteActivity(Note note)
    {
        Intent intent = new Intent(this, EditNoteActivity.class);
        if(note != null)
        {
            intent.putExtra("noteID", note.getID()); //TODO sprawdzić, czy przypadkowo gdzieś wcześniej nie pobieramy z bazy danych całej notatki
        }
        //Intent intent = new Intent(this, LabelActivity.class);
        startActivity(intent);
    }


}

