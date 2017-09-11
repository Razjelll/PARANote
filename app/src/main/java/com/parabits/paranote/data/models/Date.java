package com.parabits.paranote.data.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {

    private final short INITIAL_YEAR = 2016; //wartość która będzie odejmowana od roku, podczas uzyskiwania kodu. Odejmujemy tę wartość, aby kod zmieścił się w 4 bajtach
    private final int CODE_LENGTH = 10;

    private byte m_day;
    private byte m_month;
    private short m_year;
    private byte m_hour;
    private byte m_minute;

    public byte getDay() {return m_day;}
    public byte getMonth() {return m_month;}
    public short getYear() {return m_year;}
    public byte getHour() {return m_hour;}
    public byte getMinute() {return m_minute;}

    public static Date getNow()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        return new Date(year, month, day, hour, minute);
    }


    public Date()
    {
        Calendar calendar = Calendar.getInstance();
        m_year = (short)calendar.get(Calendar.YEAR);
        m_month = (byte)calendar.get(Calendar.MONTH);
        m_day = (byte)calendar.get(Calendar.DAY_OF_MONTH);
        m_hour = (byte)calendar.get(Calendar.HOUR);
        m_minute = (byte)calendar.get(Calendar.MINUTE);
    }


    public Date(int year, int month, int day, int hour, int minute)
    {
        setYear((short)year);
        setMonth((byte)month);
        setDay((byte)day);
        setHour((byte)hour);
        setMinute((byte)minute);
    }

    public Date(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(); //TODO zobaczyć czy z takim czymś będzie dobrze działało na różnych
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        m_year = (short)calendar.get(Calendar.YEAR);

        calendar.get(Calendar.DAY_OF_MONTH);
    }

    public Date(int code)
    {
        String codeString = String.valueOf(code);
        // jeżeli długość kodu jest mniejsza od 10 (możliwa jest tylko długość 9) dodajemy zero na początku
        if(codeString.length() != CODE_LENGTH)
        {
            codeString = "0" + codeString;
        }
        m_year = (short)(Short.parseShort(codeString.substring(0, 2)) + INITIAL_YEAR);
        m_month = Byte.parseByte(codeString.substring(2,4));
        m_day = Byte.parseByte(codeString.substring(4,6));
        m_hour = Byte.parseByte(codeString.substring(6,8));
        m_minute = Byte.parseByte(codeString.substring(8,10));
    }

    public void setDay(byte day){
        if(day > 0 && day <= 31)
        {
            m_day = day;
        }
    }

    public void setDay(int day)
    {
        setDay((byte)day);
    }

    public void addDays(int days) {
        Calendar calendar = getCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        setDateFromCalendar(calendar);
    }

    private void setDateFromCalendar(Calendar calendar) {
        m_year = (short) calendar.get(Calendar.YEAR);
        m_month = (byte) calendar.get(Calendar.MONTH);
        m_day = (byte) calendar.get(Calendar.DAY_OF_MONTH);
        m_hour = (byte) calendar.get(Calendar.HOUR);
        m_minute = (byte) calendar.get(Calendar.MINUTE);
    }

    public void addMonth(int months) {
        Calendar calendar = getCalendar();
        calendar.add(Calendar.MONTH, months);
        setDateFromCalendar(calendar);
    }

    public void setMonth(byte month)
    {
        if(month > 0 && month <= 12)
        {
            m_month = month;
        }
    }

    public void setMonth(int month)
    {
        setMonth((byte)month);
    }

    public void setYear(short year)
    {
        if(year > 2010 && year < 3000)
        {
            m_year = year;
        }
    }

    public void setYear(int year)
    {
        setYear((short) year);
    }

    public void setHour(byte hour)
    {
        if(hour >= 0 && hour < 24)
        {
            m_hour = hour;
        }
    }

    public void setHour(int hour)
    {
        setHour((byte)hour);
    }

    public void setMinute(byte minute)
    {
        if(minute >= 0 && minute < 60)
        {
            m_minute = minute;
        }
    }

    public void setMinute(int minute)
    {
        setMinute((byte) minute);
    }

    public void setTime(String time)
    {
        String[] timeParts = time.split(":");
        m_hour = Byte.parseByte(timeParts[0]);
        m_minute = Byte.parseByte(timeParts[1]);
    }

    public java.util.Date getDate()
    {
        Calendar calendar = getCalendar();
        return calendar.getTime();
    }

    public Calendar getCalendar()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, m_year);
        calendar.set(Calendar.MONTH, m_month);
        calendar.set(Calendar.DAY_OF_MONTH, m_day);
        calendar.set(Calendar.HOUR, m_hour);
        calendar.set(Calendar.MINUTE, m_minute);

        return calendar;
    }

    public int getCode()
    {
        int year = m_year - INITIAL_YEAR;
        int result = year * 100000000;
        result += m_month * 1000000;
        result += m_day * 10000;
        result += m_hour * 100;
        result += m_minute;
        return result;
    }

    public long getMilliseconds()
    {
        return getCalendar().getTimeInMillis();
    }

    @Override
    public String toString()
    {
        return m_day + ":" + m_month +"." +m_year +" " + m_hour+ ":"+ m_minute;
    }

    /**
     * Zwraca datę w formacie dd.mm.yyyy
     * @return
     */
    public String getDateString() {
        //TODO zrobić formatowanie liczb do wstawiając zero na początek
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(m_day).append(".").append(m_month).append(".").append(m_year);
        return stringBuilder.toString();
    }


}
