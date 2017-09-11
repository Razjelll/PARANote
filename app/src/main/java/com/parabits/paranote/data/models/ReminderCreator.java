package com.parabits.paranote.data.models;

import android.database.Cursor;

import com.parabits.paranote.utils.RepetitionDaysUtils;

import java.util.List;

import static com.parabits.paranote.data.database.tables.RemindersTable.*;

public class ReminderCreator implements IModelCreator<Reminder> {

    @Override
    public Reminder createFromCursor(Cursor cursor) {
        long noteID = cursor.getLong(ID_POSITION);
        int date = cursor.getInt(DATE_POSITION);
        boolean active = cursor.getInt(ACTIVE_POSITION) == 1;

        Reminder reminder = new Reminder();
        reminder.setNoteID(noteID);
        reminder.setDate(new Date(date));
        reminder.setActive(active);

        //sprawdzanie czy przypomnienie ma powtarzanie
        // jeżeli nie ma przypomnienia pattern jest null
        if(!cursor.isNull(PATTERN_POSITION))
        {
            reminder.setRepetition(new Repetition());
            boolean monthly = cursor.getInt(MONTHLY_POSITION) == 1;
            reminder.getRepetition().setMonthly(monthly);
            String patternString = cursor.getString(PATTERN_POSITION);
            List<Integer> pattern = RepetitionDaysUtils.getPattern(patternString);
            reminder.getRepetition().setDays(pattern);
            if(!cursor.isNull(END_DATE_POSITION)) //jeżeli data zakńczenia nie zotała wyznaczona
            {
                int endDate = cursor.getInt(END_DATE_POSITION);
                reminder.getRepetition().setEndDate(new Date(endDate));
            }
        }
        return reminder;
    }
}
