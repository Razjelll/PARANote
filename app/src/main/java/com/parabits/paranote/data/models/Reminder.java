package com.parabits.paranote.data.models;

import android.support.annotation.NonNull;

public class Reminder {

    public enum Type
    {
        NOTIFICATION,
        SCREEN // TODO można tę nazwę zmienić
    }


    public Reminder()
    {
        mDate = new Date();
    }

    private long m_note_id;
    /// data zawierająca date oraz godzinę o której przypomnienie ma zostać uruchomione
    private Date mDate;
    /// powtórzenia przypomnienia. Jeżeli powtórzenie nie jest uruchomione, zmienna przyjmuje wartość NULL
    private Repetition mRepetition;
    private ReminderSignal mSignal;
    /// określa w jakiej formie ma zostać pokazane powiadomienie. Możliwe jest powiadomienie za pomocą
    /// systemu notyfikacji oraz za pomocą ekranu, który będzie widoczny dopóki użytkownik go nie wyłączy
    private Type mType;
    //TODO można dodać czas przed ustalonym terminem, kiedy będzie wyświetlane powiadomienie. W notatkach to może nie byćkonieczne
    private boolean m_active;


    public long getNoteID() {return m_note_id;}
    public Date getDate() {return mDate;}
    public Repetition getRepetition() {return mRepetition;}
    public ReminderSignal getSignal() {return mSignal;}
    public Type getType() {return mType;}
    public boolean isActive(){return m_active;}

    public void setNoteID(long id ) {m_note_id = id;}
    public void setDate(Date date) {mDate = date;}
    public void setRepetition(Repetition repetition) {mRepetition = repetition;}
    public void setSignal(ReminderSignal signal) {mSignal = signal;}
    public void setType(Type type) {mType = type;}
    public void setActive(boolean active) { m_active = active;}
}
