package com.parabits.paranote.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.parabits.paranote.activities.ReminderActivity;
import com.parabits.paranote.data.database.ReminderDao;
import com.parabits.paranote.data.models.Reminder;

public class ReminderBroadcastReceiver extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent)
    {
        // TODO tutaj zrobić coś, co ma zostać wykonane jak przypominajka zacznie przypominać
        // można na przykład włączyć jakąś aktywność, z której będzie można przejśc do notatki
        // można także wysłać powiadomienie za pomocą notyfikacji
        // prawdopodobnie będzie możliwośc wyboru tego z poziomu ustawień

        // uruchomienie aktywności
        Intent activityIntent = new Intent(context, ReminderActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activityIntent);

        // uruchomienie kolejnego powtórzenia
        long reminderID = intent.getLongExtra("id", -1);
        ReminderDao reminderDao = new ReminderDao(context);
        Reminder reminder = reminderDao.get(reminderID);
        if(reminder != null)
        {
            ReminderAlarmManager alarmManager = new ReminderAlarmManager();
            alarmManager.startAlarm(reminder, context);
        }
    }
}
