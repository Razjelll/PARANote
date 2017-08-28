package com.parabits.paranote.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parabits.paranote.R;
import com.parabits.paranote.data.database.NoteDao;
import com.parabits.paranote.data.models.Date;
import com.parabits.paranote.data.models.Note;

public class EditNoteActivity extends AppCompatActivity {

    private EditText m_title_edit_text;
    private EditText m_content_edit_text;
    private Button m_save_button;

    private boolean m_edited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        setupToolbar();
        setupControls();
    }

    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupControls()
    {
        m_title_edit_text = (EditText) findViewById(R.id.title_edit_text);
        m_content_edit_text = (EditText)findViewById(R.id.content_edit_text);
        m_save_button = (Button)findViewById(R.id.save_button);

        m_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote(m_edited);
            }
        });
    }

    private void saveNote(boolean edited)
    {
        Note note = new Note();
        note.setTitle(m_title_edit_text.getText().toString());
        note.setContent(m_title_edit_text.getText().toString());
        NoteDao dao = new NoteDao(getApplicationContext());
        Date nowDate = Date.getNow();
        if(edited) //edytowanie notatki
        {
            note.setUpdateDate(nowDate);
            //TODO dorobićupdate
        } else { //zapisywanie nowej notatki
            note.setCreationDate(nowDate);
            note.setUpdateDate(nowDate);
            dao.add(note);
        }

    }

}
