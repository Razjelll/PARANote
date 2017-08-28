package com.parabits.paranote.data.models;

import com.parabits.paranote.BuildConfig;

/** Klasa opisująca powórzenie przypomnienia notatki. Powtórzenie opisuje kiedy przypominanie ma zostac
 * odtwarzane. Można ustawić przypominanie od okreslonej daty do określonej daty. Możliwe jest także ustawienie
 * dni w które powtórzenia ma zostać odtwarzane, oraz z jaką częstotliwością ma zostać odtwarzane.
 */
public class Repetition {
    /** interwał określający z jaką częstotliwością ma zostać odtwarzane przypomnienie. Interwał określany jest
    * jset w dniach*/ //TODO zobaczyć, czy nie lepiej będzie ustawić ten czas w godzinach lub minutach.
    private int mInterval;
    private boolean[] mDays;
    private Date mStartDate;
    private Date mEndDate;

    public Repetition()
    {
        mDays = new boolean[7];
    }

    public int getInterval() {return mInterval;}
    public boolean[] getDays() {return mDays;}
    public Date getStartDate(){return mStartDate;}
    public Date getEndDate(){return mEndDate;}

    public void setInterval(int interval) {mInterval = interval;}
    public void setDay(int day, boolean enabled)
    {
        //if(BuildConfig.DEBUG && (day >= 0 && day < 7)) throw new AssertionError();
        assert (day >= 0 && day < 7);
        mDays[day] = enabled;
    }
    public void setStartDate(Date date)
    {
        mStartDate = date;
    }
    public void setEndDate(Date date)
    {
        mEndDate = date;
    }


}
