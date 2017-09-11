package com.parabits.paranote.data.models;

import java.util.ArrayList;
import java.util.List;

/** Klasa opisująca powórzenie przypomnienia notatki. Powtórzenie opisuje kiedy przypominanie ma zostac
 * odtwarzane. Można ustawić przypominanie od okreslonej daty do określonej daty. Możliwe jest także ustawienie
 * dni w które powtórzenia ma zostać odtwarzane, oraz z jaką częstotliwością ma zostać odtwarzane.
 */
public class Repetition{

    public static final int DAILY = 1;
    public static final int WEEKLY = 2;
    public static final int MONTHLY= 3;
    public static final int WORKING_DAYS = 4;

    /** lista indeksów zaznaczonych dni tygodnia*/
    private List<Integer> m_days;

    /** Data rozpoczecia powtarzania. Jest to zazwyczaj data pierwszej powtórki i jest identyczna jak data ustawiona
        jako data przypomnienia*/
    private Date mStartDate;
    /** Data zakończenia przypominania. Po tej dacie przypomienie zostanie usunięte z bazy danych */
    private Date mEndDate;
    /** Wartość określa, czy przypomnienie ma charakter miesięczy czy tygodniowy.
     * - wartości true (miesięczny) - brane pod uwagę są wartości z listy m_monthly_days.
     * - wartość false (tygodniowy) - brane pod uwagę są wartości z listy m_days */
    private boolean m_monthly;

    public Repetition()
    {
        m_days = new ArrayList<>();
    }

    public Repetition(int interval)
    {
        m_days = new ArrayList<>();
        switch (interval)
        {
            case DAILY:
                setDaily(); break;
            case WEEKLY:
                setWeekly(); break;
            case MONTHLY:
                setMonthly(true); break; //TODO to chyba będzie musiało zostać zrobione troszkę inaczej\
            case WORKING_DAYS:
                setWorkingDays(); break;
            default:
                throw new IllegalArgumentException("Incorrect interval");
        }
    }

    public List<Integer> getDays(){
        return m_days;
    }

    public Date getStartDate(){return mStartDate;}
    public Date getEndDate(){return mEndDate;}
    public int getDayOfTheMOnth() {return mStartDate.getDay();}
    public boolean isMonthly() {return m_monthly;}

    public void setDays(List<Integer> days)
    {
        m_days = days;

    }
    public void setStartDate(Date date)
    {
        mStartDate = date;
    }
    public void setEndDate(Date date)
    {
        mEndDate = date;
    }

    public void setDaily()
    {
        for(int i =0; i< 7; i++)
        {
            m_days.add(i);
        }
        m_monthly = false;
    }

    public void setWorkingDays()
    {
        for(int i=0; i<5; i++)
        {
            m_days.add(i);
        }
        m_monthly = false;
    }

    public void setWeekly()
    {
        //TODO zrobić wykrywanie jaki jest dzień oraz ustawienie odpowiednich wartości
//        for(int i=0; i<7; i++)
//        {
//            mDays[i] = i == dayOfTheWeek;
//        }
        m_monthly = false;
    }

    public void setMonthly(List<Integer> days)
    {
        m_monthly = true;
    }

    public void setMonthly(boolean monthly)
    {
        m_monthly = monthly;
        //TODO zrobić coś, że by ustawić odpowiedni dzień
    }


}
