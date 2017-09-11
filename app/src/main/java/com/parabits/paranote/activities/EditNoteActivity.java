package com.parabits.paranote.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parabits.paranote.R;
import com.parabits.paranote.data.database.NoteDao;
import com.parabits.paranote.data.database.ReminderDao;
import com.parabits.paranote.data.models.Date;
import com.parabits.paranote.data.models.Note;
import com.parabits.paranote.data.models.Reminder;
import com.parabits.paranote.views.NoteView;
import com.parabits.paranote.views.ParaToolbar;
import com.parabits.paranote.views.ToolbarMenu;

public class EditNoteActivity extends AppCompatActivity {

    private final int PICK_IMAGE = 9932;

    private EditText m_title_edit_text;

    private ParaToolbar m_bottom_toolbar;

    private NoteView m_note_view;

    private Note m_note;
    /**
     * Określa, czy w momencie uruchomienia aktywności w bazie danych istnieje przypomnienie powiązane
     * z edytowaną notatką. Zmienna pomaga w decyzji co zrobić z przypomnieniem w bazie danych
     * podczas aktualizacji notaki. Przypomnienie może być usunięte, dodane lub zaktualizowane
     * w zależności do wartości zmiennej i ustawionego przypomnienia dla notatki.
     */
    private boolean m_reminder_in_db;

    private boolean m_edited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        //TODO dla edycji ustawić
        setupToolbar();
        setupControls();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        long noteID = intent.getLongExtra("noteID", -1);
        if (noteID > 0) // notatka jest edytowana
        {
            // pobieramy notatkę o podanym id z bazy danych, a następnie ustawiamy widok na podstawie
            // zawartości pola content notatki. Zostanie takze ustawiony tytuł notatki
            // za bazy pobierane jest także przypomnienie
            NoteDao noteDao = new NoteDao(getApplicationContext());
            m_note = noteDao.get(noteID);
            m_note_view.init(m_note.getContent());
            m_title_edit_text.setText(m_note.getTitle());
            ReminderDao reminderDao = new ReminderDao(getApplicationContext());
            Reminder reminder = reminderDao.get(noteID);
            if (reminder != null) {
                m_note.setReminder(reminder);
                m_reminder_in_db = true;
            }
            m_edited = true;
        } else // tworzenie nowej notatki
        {
            m_note = new Note();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupControls() {
        m_title_edit_text = (EditText) findViewById(R.id.title_edit_text);
        m_bottom_toolbar = (ParaToolbar) findViewById(R.id.bottom_toolbar);
        m_note_view = (NoteView) findViewById(R.id.note_view);

        ToolbarMenu menu = new ToolbarMenu();
        menu.addItem(0, android.R.drawable.ic_input_add, getString(R.string.text), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_note_view.addTextElement();
            }
        });
        menu.addItem(0, android.R.drawable.ic_input_add, getString(R.string.photo), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select pircture"), PICK_IMAGE);
            }
        });
        m_bottom_toolbar.addContextButton(0, android.R.drawable.ic_input_add, "AddText", menu);


        m_bottom_toolbar.addContextButton(android.R.drawable.ic_menu_report_image, "dada", null);
        m_bottom_toolbar.addContextButton(android.R.drawable.ic_menu_report_image, "dada", null);
        m_bottom_toolbar.addContextButton(android.R.drawable.ic_menu_report_image, "dada", null);
        m_bottom_toolbar.addContextButton(android.R.drawable.ic_menu_report_image, "dada", null);
        m_bottom_toolbar.addContextButton(android.R.drawable.ic_menu_report_image, "dada", null);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                //Uri uri = Uri.parse("content://media/external/images/media/107");
                //addImageElement(uri);
                m_note_view.addImageElement(uri);
            }
        }
    }

    /**
     * Zapisuje utworzoną notatkę w bazie danych. Jeżeli notatka jest edytowana zostanie zaktualizowana.
     * Jeżeli tworzona jest nowa notatka zostanie zapisana w bazie dancyh.
     * Na początku sprawdzane jest wypełnienie wymaganych pól. Jeżeli jakieś z tych pól nie jest
     * wypełnione zapis jest niewykonywany. W pierwszej kolejności zapisywana jest zawartość notatki.
     * Jeżeli zapis się powiedzie i zostało ustawione przypomninie nastepuje jego zapis do bazy danych;
     * //TODO postanowić co zrobić w przypadku niepowodzenia zapisu przypomnienia
     *
     * @param edited określa czy notatka była edytowana
     *               true - notatka będzie aktualizowana w bazie danych
     *               false - w bazie zostanie zapisana nowa notatka
     * @return poprawność zapisu notatki w bazie danych
     */
    private boolean saveNote(boolean edited) {
        if (!validate())
            return false; // w przypadku nie wypełnienia wymaganych pól zapis do bazy nie będzie kontynuowany
        m_note.setTitle(m_title_edit_text.getText().toString());
        String content = m_note_view.toString();
        m_note.setContent(content);
        NoteDao noteDao = new NoteDao(getApplicationContext());
        Date nowDate = Date.getNow();
        if (edited) {
            m_note.setUpdateDate(nowDate);
            //TODO podczas aktualizacji dodać sprawdzanie czy obrazek został zapisany w pamięci aplikacji i usunięcie go, obrazy z galerri zostawić
            boolean success = noteDao.update(m_note);
            if (success) {
                success = saveReminder(m_note.getReminder());
                //TODO można dodać komunikat o tym, że nie udała się tylko aktualizacja przypomnienia
            }
            if (success) {
                Toast.makeText(getApplicationContext(), getString(R.string.success_update_note), Toast.LENGTH_SHORT).show(); //udana aktualizacja
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.failed_save_note), Toast.LENGTH_SHORT).show(); // nie udana aktualziacja
            }
            return success;
            //TODO zrobić aktualizację notatki, akutalizację lub usunięcie przypomnienia
        } else // zapisanie nowej notatki w bazie danych
        {
            m_note.setCreationDate(nowDate);
            m_note.setUpdateDate(nowDate);
            long savedNoteID = noteDao.add(m_note); //zapisywanie notatki
            if (savedNoteID > 0 && m_note.getReminder() != null) { // sprawdzanie czy zapis notatki się udał i czy przypomnienie zostało przypisane
                m_note.getReminder().setNoteID(savedNoteID); // ustawienie numeru id do przypomnienie
                if (saveReminder(m_note.getReminder())) //zapisywanie przypomnienia. Jezeli przypomnienie nie zostało dodane nic nie jest zapisywana i zwracana jest wartość true
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.success_save_note), Toast.LENGTH_SHORT).show(); //komunikat o powodzeniu
                    return true;
                } else {
                    //TODO zastanowić się, czy usunąć wcześniej zapisaną notatkę, czy wyświetlić komunikat o niepowodzeniu zapisu powiadomienia i zostawić notatkę
                    // zostawienie notatki chyba będzie lepszym pomysłem, ponieważ nie będzie trzeba ustawiać jeszcze raz
                }
            }
        }
        // jeżeli dojdzie do tego miejsca, oznacza to, że zapis się nie udał
        Toast.makeText(getApplicationContext(), getString(R.string.failed_save_note), Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Zapisuje, usuwa lub aktualizuje przypomnienie w bazie danych
     *
     * @param reminder przypomnienie, które zostanie zapisane w bazie danych
     * @return powodzenie operacji
     */
    private boolean saveReminder(Reminder reminder) {
        ReminderDao reminderDao = new ReminderDao(getApplicationContext());
        if (m_note.getReminder() != null && !m_reminder_in_db) // dodawanie przypomnienia
        {
            return reminderDao.save(m_note.getReminder());
        } else if (m_note.getReminder() != null && m_reminder_in_db) {
            return reminderDao.update(m_note.getReminder());
        } else if (m_note.getReminder() == null && m_reminder_in_db) {
            return reminderDao.delete(m_note.getID());
        }
        // jeżeli m_reminder_in_db = false i nie ustawiono nowego przypomnienia nie robimy nic
        return true;
    }

    /**
     * Sprawdza poprawność wypełnienia formularza.
     * Warunki
     * - tytuł notatki nie może być pusty
     *
     * @return poprawność notatki
     */
    private boolean validate() {
        if (m_title_edit_text.getText().toString().isEmpty()) {
            // jeżeli pole tytuł nie zostanie wypełniony wyświetlona zostanie informacja o konieczności
            // wypełnienia tego pola.
            m_title_edit_text.setError(getString(R.string.empty_field));
            m_title_edit_text.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_note, menu);
        return true;
    }

    private final int SAVE_BUTTON_RESOURCE = R.id.save_button;
    private final int ADD_REMINDER_RESOURCE = R.id.add_reminder;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case SAVE_BUTTON_RESOURCE:
                if (saveNote(m_edited)) {
                    finish(); // jeżeli zapis się udał zamykamy obecną aktywność
                }
                break;
            case ADD_REMINDER_RESOURCE:
                ReminderDialog dialog = new ReminderDialog();
                dialog.setCallback(new ReminderDialog.Callback() {
                    @Override
                    public void onDialogOk(Reminder reminder) {
                        Toast.makeText(getApplicationContext(), "Zamknięto dialog ", Toast.LENGTH_SHORT).show();
                        m_note.setReminder(reminder); // noteID zostanie ustawione przy zapisywaniou notatki w bazie danych
                    }
                });
                dialog.setReminder(m_note.getReminder());
                dialog.show(getFragmentManager(), "ReminderDialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
