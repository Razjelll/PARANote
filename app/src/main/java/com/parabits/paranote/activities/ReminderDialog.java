package com.parabits.paranote.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TimePicker;

import com.parabits.paranote.R;
import com.parabits.paranote.data.models.Date;
import com.parabits.paranote.data.models.Reminder;
import com.parabits.paranote.data.models.Repetition;

import java.util.Calendar;
import java.util.List;

public class ReminderDialog extends DialogFragment {

    private View m_date_button; // na razie zostawiamy View, ponieważ forma przycisków może ulec zmianie
    private View m_time_button;
    private View m_repetition_button;
    private View m_end_date_button;

    private Reminder m_reminder;

    private Callback m_callback;
    private boolean m_date_chosen;

    public interface Callback {
        void onDialogOk(Reminder reminder);
    }

    public void setCallback(Callback callback) {
        m_callback = callback;
    }

    public void setReminder(Reminder reminder) {
        m_reminder = reminder;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        init();

    }

    private void init() {

    }

    private final int TODAY_ID = R.string.today;
    private final int TOMMOROW_ID = R.string.tomorrow;
    private final int WEEK_ID = R.string.week;
    private final int MONTH_ID = R.string.month;
    private final int ANOTHER_DATE_ID = R.string.another_date;

    private final int MORNING_ID = 1;
    private final int MIDDAY_ID = 2;
    private final int EVENING_ID = 3;
    private final int NIGHT_ID = 4;
    private final int CHOOSE_TIME_ID = R.string.choose_time;

    private final String MORNING_TIME = "06:00";
    private final String MIDDAY_TIME = "12:00";
    private final String EVENING_TIME = "17:00";
    private final String NIGHT_TIME = "22:00";

    private final int NOT_REPEAT_ID = R.string.not_repeat;
    private final int DAILY_ID = R.string.daily;
    private final int WEEKLY_ID = R.string.weekly;
    private final int MONTHLY_ID = R.string.monthly;
    private final int WORKING_DAYS_ID = R.string.workings_days;
    private final int ANOTHER_REPEATING_ID = R.string.another_reapiting;

    private final int WITHOUT_END_DATE_ID = R.string.without_end_date;
    private final int SET_DATE_ID = R.string.set_date;

    private View getView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_reminder, null);
        setupDateButton(view);
        setupTimeButton(view);
        setupRepetitionButton(view);
        setupEndDateButton(view);

        if (m_reminder != null) {
            setData();
        } else // wypełnianie domyślnymi danymi
        {
            m_reminder = new Reminder();
            setDefaultValues();
        }
        return view;
    }

    private void setData() {
        setDate(m_reminder.getDate(), m_date_button);
        String timeString = getFormattedTime(m_reminder.getDate().getHour()) + ":" + getFormattedTime(m_reminder.getDate().getMinute());
        setTime(timeString);
        if (m_reminder.getRepetition() != null) {
            String repetitionPatternString = getRepetitionPatternString(m_reminder.getRepetition().getDays(), m_reminder.getRepetition().isMonthly());
            setRepetitionButtonText(repetitionPatternString);
            if (m_reminder.getRepetition().getEndDate() != null) {
                setDate(m_reminder.getRepetition().getEndDate(), m_end_date_button);
            } else {
                setButtonText(getString(R.string.not_set), m_end_date_button);
            }
        } else {
            setButtonText(getString(R.string.not_repeat), m_repetition_button);
            setButtonText(getString(R.string.not_set), m_end_date_button);
        }
    }

    private void setDefaultValues() {
        //TODO zobaczyć, czy nie będzie trzeba od razu ustawić wszystkich wartości do m_reminder
        setDate(Date.getNow(), m_date_button);
        // domyślnie czas ustawiany jest na nastepną godzinę
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        int hour = calendar.get(Calendar.HOUR);
        setTime(hour + ":00");
        m_reminder.getDate().setHour((byte) hour);
        setRepetitionButtonText(getString(R.string.not_repeat));
        setButtonText(getString(R.string.not_set), m_end_date_button);
    }


    private void setupDateButton(View view) {
        m_date_button = view.findViewById(R.id.date_button);
        final PopupMenu dateButtonMenu = new PopupMenu(getActivity(), m_date_button);
        // wstawianie wartości do menu
        final Menu buttonMenu = dateButtonMenu.getMenu();
        buttonMenu.add(0, TODAY_ID, 0, TODAY_ID);
        buttonMenu.add(0, TOMMOROW_ID, 0, TOMMOROW_ID);
        buttonMenu.add(0, WEEK_ID, 0, WEEK_ID);
        buttonMenu.add(0, ANOTHER_DATE_ID, 0, ANOTHER_DATE_ID);

        dateButtonMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                m_reminder.setDate(Date.getNow()); // na początku ustawiamy dzisiajeszą datę
                switch (menuItem.getItemId()) {
                    case TODAY_ID:
                        setDate(m_reminder.getDate(), m_date_button);
                        break;
                    case TOMMOROW_ID:
                        m_reminder.getDate().addDays(1);
                        setDate(m_reminder.getDate(), m_date_button);
                        break;
                    case WEEK_ID:
                        m_reminder.getDate().addDays(7);
                        setDate(m_reminder.getDate(), m_date_button);
                        break;
                    case MONTH_ID:
                        m_reminder.getDate().addMonth(1);
                        setDate(m_reminder.getDate(), m_date_button);
                        break;
                    case ANOTHER_DATE_ID:
                        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                Date date = m_reminder.getDate();
                                date.setYear(datePicker.getYear());
                                date.setMonth(datePicker.getMonth());
                                date.setDay(datePicker.getDayOfMonth());
                                setDate(date, m_date_button);
                                m_reminder.setDate(date);
                            }
                        }, m_reminder.getDate().getYear(), m_reminder.getDate().getMonth(), m_reminder.getDate().getDay());
                        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                        dialog.show();
                        break;
                    default:
                        return false;
                }
                m_date_chosen = true;
                return true;
            }
        });

        m_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateButtonMenu.show();
            }
        });
    }

    private void setDate(Date date, View button) {
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(getActivity());
        ((Button) button).setText(dateFormat.format(date.getDate()));
    }

    private void setButtonText(String text, View button) {
        ((Button) button).setText(text);
    }

    private void setupTimeButton(View view) {
        m_time_button = view.findViewById(R.id.time_button);
        final PopupMenu popupMenu = new PopupMenu(getActivity(), m_time_button);
        final Menu buttonMenu = popupMenu.getMenu();
        //TODO zrobić pobieranie wartości z preferencji
        buttonMenu.add(0, MORNING_ID, 0, MORNING_TIME);
        buttonMenu.add(0, MIDDAY_ID, 0, MIDDAY_TIME);
        buttonMenu.add(0, EVENING_ID, 0, EVENING_TIME);
        buttonMenu.add(0, NIGHT_ID, 0, NIGHT_TIME);
        buttonMenu.add(0, CHOOSE_TIME_ID, 0, CHOOSE_TIME_ID);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final String time;
                switch (menuItem.getItemId()) {
                    case MORNING_ID:
                        time = MORNING_TIME;
                        setTime(time);
                        break;
                    case MIDDAY_ID:
                        time = MIDDAY_TIME;
                        setTime(time);
                        break;
                    case EVENING_ID:
                        time = EVENING_TIME;
                        setTime(time);
                        break;
                    case NIGHT_ID:
                        time = NIGHT_TIME;
                        setTime(time);
                        break;
                    case CHOOSE_TIME_ID:
                        TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                //TODO zrobić to dla innych wersji
                                String timeString;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    timeString = getFormattedTime(timePicker.getHour()) + ":" + getFormattedTime(timePicker.getMinute());
                                } else {
                                    timeString = getFormattedTime(timePicker.getCurrentHour()) + ":" + getFormattedTime(timePicker.getCurrentMinute());
                                }
                                setTime(timeString);
                            }
                        }, 18, 0, true);
                        dialog.show();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        m_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });
    }

    private String getFormattedTime(int time) {
        return String.format("%02d", time);
    }

    private void setTime(String time) {
        m_reminder.getDate().setTime(time);
        ((Button) m_time_button).setText(time);
    }

    private void setupRepetitionButton(View view) {
        m_repetition_button = view.findViewById(R.id.repetition_button);
        final PopupMenu popupMenu = new PopupMenu(getActivity(), m_repetition_button);
        Menu menu = popupMenu.getMenu();
        menu.add(0, NOT_REPEAT_ID, 0, NOT_REPEAT_ID);
        menu.add(0, DAILY_ID, 0, DAILY_ID);
        menu.add(0, WEEKLY_ID, 0, WEEKLY_ID);
        menu.add(0, MONTHLY_ID, 0, MONTHLY_ID);
        menu.add(0, WORKING_DAYS_ID, 0, WORKING_DAYS_ID);
        menu.add(0, ANOTHER_REPEATING_ID, 0, ANOTHER_REPEATING_ID);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case NOT_REPEAT_ID:
                        m_reminder.setRepetition(null); //usuwamy przypomnienie
                        setRepetitionButtonText(getString(R.string.lack));
                        m_end_date_button.setEnabled(false); // jeżeli nie ma ustawionego powtarzania guzik ustawiania daty końcowej jest wyłączony
                        break;
                    case DAILY_ID:
                        m_reminder.setRepetition(new Repetition(Repetition.DAILY));
                        setRepetitionButtonText(getString(R.string.daily));
                        m_end_date_button.setEnabled(true); // jeżeli ustawimy jakiekolwiek powtarzanie guzik ustawiania daty końcowej jest aktywny
                        break;
                    case WEEKLY_ID:
                        m_reminder.setRepetition(new Repetition(Repetition.WEEKLY));
                        setRepetitionButtonText(getString(R.string.weekly));
                        m_end_date_button.setEnabled(true);
                        break;
                    case MONTHLY_ID:
                        m_reminder.setRepetition(new Repetition(Repetition.MONTHLY));
                        setRepetitionButtonText(getString(R.string.monthly));
                        m_end_date_button.setEnabled(true);
                        break;
                    case WORKING_DAYS_ID:
                        m_reminder.setRepetition(new Repetition(Repetition.WORKING_DAYS));
                        setRepetitionButtonText(getString(R.string.workings_days));
                        m_end_date_button.setEnabled(true);
                        break;
                    case ANOTHER_REPEATING_ID:
                        RepetitionDialog dialog = new RepetitionDialog();
                        if (m_reminder.getRepetition() != null) {
                            dialog.setRepetition(m_reminder.getRepetition().getDays(), m_reminder.getRepetition().isMonthly());
                        }
                        dialog.setCallback(new RepetitionDialog.Callback() {
                            @Override
                            public void onDialogOk(List<Integer> selectedIndexList, boolean monthly) {
                                m_reminder.setRepetition(new Repetition());
                                m_reminder.getRepetition().setMonthly(monthly);
                                m_reminder.getRepetition().setDays(selectedIndexList);

                                setRepetitionButtonText(getRepetitionPatternString(selectedIndexList, monthly));
                                m_end_date_button.setEnabled(true);

                            }
                        });
                        dialog.show(getFragmentManager(), "RepetitionDialog");
                        break;
                }
                return true;
            }
        });


        m_repetition_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });
    }

    private void setupEndDateButton(View view) {
        m_end_date_button = view.findViewById(R.id.end_date_button);
        m_end_date_button.setEnabled(false); //początkowo przycisk jest wyłączony. Aktywny staje się w momencie ustawienia powtarzania

        final PopupMenu popupMenu = new PopupMenu(getActivity(), m_end_date_button);
        Menu menu = popupMenu.getMenu();
        menu.add(0, WITHOUT_END_DATE_ID, 0, WITHOUT_END_DATE_ID);
        menu.add(0, SET_DATE_ID, 0, SET_DATE_ID);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case WITHOUT_END_DATE_ID:
                        ((Button) m_end_date_button).setText(getString(WITHOUT_END_DATE_ID));
                        m_reminder.getRepetition().setEndDate(null); //TODO tutaj pomyśleć czy zostawić tak, czy zaznaczyć, że daty nie ma
                        break;
                    case SET_DATE_ID:
                        int selectedYear;
                        int selectedMonth;
                        int selectedDay;
                        if (m_reminder.getDate() != null) {
                            selectedYear = m_reminder.getDate().getYear();
                            selectedMonth = m_reminder.getDate().getMonth();
                            selectedDay = m_reminder.getDate().getDay();
                        } else {
                            Date date = Date.getNow();
                            selectedYear = date.getYear();
                            selectedMonth = date.getMonth();
                            selectedDay = date.getDay();
                        }

                        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                Date date = m_reminder.getDate();
                                date.setYear(datePicker.getYear());
                                date.setMonth(datePicker.getMonth());
                                date.setDay(datePicker.getDayOfMonth());
                                assert m_reminder.getRepetition() != null;
                                m_reminder.getRepetition().setEndDate(date);
                                setDate(date, m_end_date_button);
                            }
                        }, selectedYear, selectedMonth, selectedDay);
                        dialog.getDatePicker().setMinDate(m_reminder.getDate().getMiliseconds());
                        dialog.show();
                        break;
                }
                return true;
            }
        });

        m_end_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });
    }

    /**
     * Przygotowuje napis, który będzie widoczny na przycisku przypomnieniea po wybraniu określonego wzorca.
     *
     * @return napis przedstawiający wybrany wzorzec powtórzeń
     */
    private String getRepetitionPatternString(List<Integer> selectedIndexList, boolean monthly) {
        StringBuilder stringBuilder = new StringBuilder();
        if (monthly) {
            stringBuilder.append(getString(R.string.monthly) + " : ");
            for (int i = 0; i < selectedIndexList.size(); i++) {
                // zwiększamy indeks o 1 ponieważ chcemy otrzymać dzień miesiąca
                stringBuilder.append(String.valueOf(selectedIndexList.get(i) + 1));
                if (i != selectedIndexList.size() - 1) {
                    stringBuilder.append(";");
                }
            }
        } else {
            stringBuilder.append(getString(R.string.weekly) + " : ");
            String[] daysOfTheWeek = getResources().getStringArray(R.array.days_of_the_week_abbr);
            for (int i = 0; i < selectedIndexList.size(); i++) {
                stringBuilder.append(daysOfTheWeek[i]);
                if (i != selectedIndexList.size() - 1) {
                    stringBuilder.append(";");
                }
            }
        }
        return stringBuilder.toString();
    }

    private void setRepetitionButtonText(String text) {
        ((Button) m_repetition_button).setText(text);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(getString(R.string.reminder))
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (m_callback != null) {
                            //TODO zajrzeć, czy wszystkie potrzebne wartości są ustawione
                            m_callback.onDialogOk(m_reminder);
                        }
                        //TODO zrobić zapisywanie i przekazanie wyników i jescze zamknięcie dialogu
                    }
                })
                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                        //TODO zobaczyć czy nie trzeba w jakiś specjalny sposób usunąć dialogu
                    }
                });
        //TODO sprawdzić czy przypomnienie zostało już wcześniej dodane i wyświetlić przycisk usuwania
        View view = getView(LayoutInflater.from(getActivity()));
        dialogBuilder.setView(view);
        return dialogBuilder.create();
    }
}
