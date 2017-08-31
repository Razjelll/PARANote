package com.parabits.paranote.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.parabits.paranote.R;
import com.parabits.paranote.data.database.NoteDao;
import com.parabits.paranote.data.models.Date;
import com.parabits.paranote.data.models.Note;
import com.parabits.paranote.views.NoteView;
import com.parabits.paranote.views.ParaToolbar;

public class EditNoteActivity extends AppCompatActivity {

    private final int PICK_IMAGE = 9932;

    private EditText m_title_edit_text;
    /*private ImageButton m_save_button;
    private ImageButton m_add_text_button;
    private ImageButton m_photo_button;*/

    private ParaToolbar m_bottom_toolbar;

    private NoteView m_note_view;


    private boolean m_edited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        setupToolbar();
        setupControls();
        getData();
    }

    private void getData()
    {
        Intent intent = getIntent();
        Note note = intent.getParcelableExtra("note");
        if(note != null)
        {
            m_note_view.init(note.getContent());
        }
    }

    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupControls()
    {
        m_title_edit_text = (EditText) findViewById(R.id.title_edit_text);
        /*m_save_button = (ImageButton)findViewById(R.id.save_button);
        m_add_text_button = (ImageButton) findViewById(R.id.add_note_button);
        m_photo_button = (ImageButton) findViewById(R.id.photo_button);*/
        m_bottom_toolbar = (ParaToolbar) findViewById(R.id.bottom_toolbar);

        m_note_view = (NoteView) findViewById(R.id.note_view);

        /*m_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote(m_edited);
            }
        });

        m_add_text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addTextElement();
                m_note_view.addTextElement();
            }
        });

        m_photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select pircture"), PICK_IMAGE);
            }
        });*/
        m_bottom_toolbar.addConstantsButton(android.R.drawable.ic_input_add, "AddText", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_note_view.addTextElement();
            }
        }, 0);

        m_bottom_toolbar.addConstantsButton(android.R.drawable.ic_menu_camera, "AddPhoto", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select pircture"), PICK_IMAGE);
            }
        }, 1);

        m_bottom_toolbar.addContextButton(android.R.drawable.ic_menu_report_image, "dada", null);
        m_bottom_toolbar.addContextButton(android.R.drawable.ic_menu_report_image, "dada", null);
        m_bottom_toolbar.addContextButton(android.R.drawable.ic_menu_report_image, "dada", null);
        m_bottom_toolbar.addContextButton(android.R.drawable.ic_menu_report_image, "dada", null);
        m_bottom_toolbar.addContextButton(android.R.drawable.ic_menu_report_image, "dada", null);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK)
        {
            if(data != null)
            {
                Uri uri = data.getData();
                //Uri uri = Uri.parse("content://media/external/images/media/107");
                //addImageElement(uri);
                m_note_view.addImageElement(uri);
            }
        }
    }

    private void saveNote(boolean edited)
    {
        Note note = new Note();
        note.setTitle(m_title_edit_text.getText().toString());
        String content = m_note_view.toString();
        note.setContent(content);
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
