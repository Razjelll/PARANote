package com.parabits.paranote.data.models;

import java.util.ArrayList;
import java.util.List;

/** Klasa, która przechowuje informacje o niestandardowym powtórzeniu. Określa w jakie dni tygodnia
 * lub w jakie dni miesiąca ma zoastać wykonane przypomnienie.
 */
public class RepetitionPattern {

    private List<Integer> m_weekly_days;
    private List<Integer> m_monthly_days;
    private boolean m_monthly;

    public RepetitionPattern()
    {
        m_weekly_days = new ArrayList<>();
        m_monthly_days = new ArrayList<>();
        m_monthly = false;
    }

    public RepetitionPattern(List<Integer> days, boolean monthly)
    {
        if(monthly)
        {
            if(days != null){
                m_monthly_days = days;

            } else {
                m_monthly_days = new ArrayList<>();
            }
            m_weekly_days = new ArrayList<>();
        } else {
            if(days != null)
            {
                m_weekly_days = days;
            } else {
                m_weekly_days = new ArrayList<>();
            }
            m_monthly_days = new ArrayList<>();
        }
        m_monthly = monthly;
    }

    public void setDays(List<Integer> days)
    {
        if(m_monthly) {
            m_monthly_days = days;
        } else {
            m_weekly_days = days;
        }
    }

    public void setDays(List<Integer> days, boolean monthly)
    {
        if(monthly) {
            m_monthly_days = days;
        } else {
            m_weekly_days = days;
        }
    }

    public List<Integer> getDays()
    {
        return m_monthly ? m_monthly_days : m_weekly_days;
    }

    public List<Integer> getDays(boolean monthly)
    {
        return monthly ? m_monthly_days : m_weekly_days;
    }

    public boolean isMonthly()
    {
        return m_monthly;
    }

    public void setMonthly(boolean monthly)
    {
        m_monthly = monthly;
    }
}
