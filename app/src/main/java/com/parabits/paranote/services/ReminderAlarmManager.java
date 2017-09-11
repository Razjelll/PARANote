package com.parabits.paranote.services;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.parabits.paranote.data.models.Reminder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReminderAlarmManager {

    public void startAlarm(Reminder reminder, Context context)
    {
        PendingIntent pendingIntent = getPendingIntent(reminder.getNoteID(), context);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        long alarmTime = getAlarmTime(reminder);
        if(alarmTime > 0) // jeżeli istnieje kolejne powtórzenie
        {
            Date date = new Date(alarmTime);
            Log.d("startAlarm", date.toString());
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime,pendingIntent);
        }
        // w przeciwnym przypadku kolejny alarm nie jest ustawiany
    }

    public void cancelAlarm(long reminderID, Context context)
    {
        PendingIntent pendingIntent = getPendingIntent(reminderID, context);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private PendingIntent getPendingIntent(long reminderID, Context context)
    {
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        intent.putExtra("id", reminderID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)reminderID, intent, 0);
        return pendingIntent;
    }

    private long getAlarmTime(Reminder reminder)
    {
        if(!reminder.isActive())
        {
            return -1; // jeżeli przypomnienie jest nieaktywne nie zostanie ustawiony następny budzik
            // TODO podczas aktualizacji notatki będzie trzeba wyłączać ustawione alarmy i włączać je po zapisaniu w bazie danych
        }
        Calendar todayCalendar = Calendar.getInstance();
        Calendar reminderDateCalendar = reminder.getDate().getCalendar();
        if(reminderDateCalendar.after(todayCalendar)) // ustawianie pierwszego alamru
        {
            return reminderDateCalendar.getTimeInMillis();
        }
        Calendar nextAlarmCalendar;
        if(reminder.getRepetition() != null) // jeżeli powtarzanie nie jest puste, oznacza to, że jest
        {
            if(reminder.getRepetition().isMonthly())
            {
                int nextDayOfTheMonth = getNextDayOfTheMonth(todayCalendar.get(Calendar.DAY_OF_MONTH), reminder.getRepetition().getDays());
                nextAlarmCalendar = getDateNextDayOfTheMonth(todayCalendar, nextDayOfTheMonth);
            } else { //powtórzenie jest co tydzień
                int nextDayOfTheWeek = getNextDayOfTheWeek(todayCalendar.get(Calendar.DAY_OF_WEEK), reminder.getRepetition().getDays());
                nextAlarmCalendar = getDateNextDayOfTheWeek(todayCalendar, nextDayOfTheWeek);
            }
            if(reminder.getRepetition().getEndDate() != null) // jeżeli data zakończenia powtórzeń została ustalona
            {
                Calendar endDateCalendar = reminder.getRepetition().getEndDate().getCalendar();
                if(nextAlarmCalendar.after(endDateCalendar)) // jeżeli wyznaczona data jest po dacie ostatniego powtórzenia nie ustawiamy alarmu
                {
                    return -1;
                }
            }
            return nextAlarmCalendar.getTimeInMillis();
        }
        return -1; // oznacza to, że alarm nie zostanie ustawiony

    }

    private Calendar getDateNextDayOfTheWeek(Calendar todayCalendar, int dayOfTheWeek)
    {
        Calendar resultCalendar = todayCalendar;
        while(resultCalendar.get(Calendar.DAY_OF_WEEK) != dayOfTheWeek)
        {
            resultCalendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        return resultCalendar;
    }

    private Calendar getDateNextDayOfTheMonth(Calendar todayCalendar, int dayOfTheMonth)
    {
        Calendar resultCalendar = todayCalendar;
        while(resultCalendar.get(Calendar.DAY_OF_MONTH) != dayOfTheMonth)
        {
            resultCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return resultCalendar;
    }

    private int getNextDayOfTheWeek(int todayDayOfTheWeek, List<Integer> selectedRemindersDays)
    {
        assert selectedRemindersDays.size() > 0;
        for(int i=0; i<selectedRemindersDays.size(); i++)
        {
            if(todayDayOfTheWeek < selectedRemindersDays.get(i))
            {
                return selectedRemindersDays.get(i);
            }
        }
        // jeżeli nie znaleziono późniejszego dnia tygodnia, oznacza to, że powtórzenie będzie w nastepnym tygodniu
        // dlatego zwracamy pierwszy zaznaczony dzień tygodnia
        return selectedRemindersDays.get(0);
    }

    private int getNextDayOfTheMonth(int todayDayOfTheMonth, List<Integer> selectedRepetitionDays)
    {
        // TODO tutaj będzie troszkę ciężej, ponieważ będzie trzeba uwzględnić możliwość zaznaczenia ostatniego dnia

        return 0;
    }

}
