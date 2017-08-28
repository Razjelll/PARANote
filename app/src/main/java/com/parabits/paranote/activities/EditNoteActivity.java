package com.parabits.paranote.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.parabits.paranote.R;
import com.parabits.paranote.data.database.NoteDao;
import com.parabits.paranote.data.models.Date;
import com.parabits.paranote.data.models.Note;
import com.parabits.paranote.data.parser.NoteElement;

import java.util.ArrayList;
import java.util.List;

public class EditNoteActivity extends AppCompatActivity {

    private final int PICK_IMAGE = 9932;

    private EditText m_title_edit_text;
    private ViewGroup m_note_container;
    private List<View> m_note_views;
    private ImageButton m_save_button;
    private ImageButton m_add_text_button;
    private ImageButton m_photo_button;

    private List<NoteElement> m_note_elements;

    private boolean m_edited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        setupToolbar();
        setupControls();

        m_note_views = new ArrayList<>();
    }

    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupControls()
    {
        m_title_edit_text = (EditText) findViewById(R.id.title_edit_text);
        m_note_container = (ViewGroup) findViewById(R.id.note_container);
        m_save_button = (ImageButton)findViewById(R.id.save_button);
        m_add_text_button = (ImageButton) findViewById(R.id.add_note_button);
        m_photo_button = (ImageButton) findViewById(R.id.photo_button);
        m_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote(m_edited);
            }
        });

        m_add_text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTextElement();
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
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK)
        {
            if(data != null)
            {
                Uri uri = data.getData();
                addImageElement(uri);
            }
        }
    }

    private void addImageElement(Uri uri)
    {
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageURI(uri);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setAdjustViewBounds(true);
        m_note_container.addView(imageView);
        m_note_views.add(imageView);
    }

    private void addTextElement()
    {
        EditText editText = new EditText(getApplicationContext());
        m_note_container.addView(editText);
        m_note_views.add(editText);
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
