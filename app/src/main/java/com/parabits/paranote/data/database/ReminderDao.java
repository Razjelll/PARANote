package com.parabits.paranote.data.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.parabits.paranote.data.models.Reminder;
import com.parabits.paranote.data.models.ReminderCreator;

import java.util.ArrayList;
import java.util.List;

import static com.parabits.paranote.data.database.tables.RemindersTable.*;

public class ReminderDao {

    private final String PATTERN_SEPARATOR = ";";

    private ContentResolver m_content_resolver;

    public ReminderDao(Context context)
    {
        m_content_resolver = context.getContentResolver();
    }

    public boolean add(Reminder reminder)
    {
        ContentValues values = new ContentValues();
        values.put(ID, reminder.getNoteID());
        values.put(DATE, reminder.getDate().getCode());
        values.put(ACTIVE, reminder.isActive() ? 1 : 0);
        if(reminder.getRepetition() != null) {
            values.put(MONTHLY, reminder.getRepetition().isMonthly());
            values.put(PATTERN, getDaysPattern(reminder.getRepetition().getDays()));
            if(reminder.getRepetition().getEndDate() != null)
            {
                values.put(END_DATE, reminder.getRepetition().getEndDate().getCode());
            }
            else
            {
                values.putNull(END_DATE);
            }
        }
        else // jeżeli nie ustawiono przypomnienia wstawiamy we wszystkie powiązane pola wartość null
        {
            values.putNull(MONTHLY);
            values.putNull(PATTERN);
            values.putNull(END_DATE);
        }
        Uri uri = NotesProvider.getUri(NotesProvider.Table.REMINDERS);
        Uri resultUri = m_content_resolver.insert(uri, values);
        return resultUri != null;
    }

    private String getDaysPattern(List<Integer> selectedDays)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i< selectedDays.size(); i++)
        {
            stringBuilder.append(i);
            if(i != selectedDays.size() -1)
            {
                stringBuilder.append(PATTERN_SEPARATOR);
            }
        }
        return stringBuilder.toString();
    }

    public boolean delete(long noteID) // id notatki jest jednocześnie id przypomnienia, ponieważ notatka może miec tylko jedno przypomnienie
    {
        if(noteID > 0)
        {
            Uri uri = NotesProvider.getUri(NotesProvider.Table.REMINDERS, noteID);
            int deletedRows = m_content_resolver.delete(uri, null, null);
            return deletedRows >0;
        }
        return false;
    }

    public Reminder get(long noteID)
    {
        if(noteID > 0)
        {
            String[] columns = {"*"};
            Uri uri = NotesProvider.getUri(NotesProvider.Table.REMINDERS, noteID);
            Cursor cursor = m_content_resolver.query(uri, columns, null, null, null);
            assert cursor != null;
            if(cursor.moveToFirst())
            {
                ReminderCreator creator = new ReminderCreator();
                return creator.createFromCursor(cursor);
            }
        }
        return null;
    }

    public List<Reminder> getAll()
    {
        List<Reminder> remindersList = new ArrayList<>();
        String[] columns = {"*"};
        Uri uri = NotesProvider.getUri(NotesProvider.Table.REMINDERS);
        Cursor cursor = m_content_resolver.query(uri, columns, null, null, null);
        assert cursor != null;
        if(cursor.moveToFirst())
        {
            Reminder reminder;
            ReminderCreator creator = new ReminderCreator();
            do{
                reminder = creator.createFromCursor(cursor);
                if(reminder != null)
                {
                    remindersList.add(reminder);
                }
            } while(cursor.moveToNext());
        }
        return remindersList;
    }

    //TODO dorobić aktualizację
}
