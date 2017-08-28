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

    public void setMonth(byte month)
    {
        if(month > 0 && month <= 12)
        {
            m_month = month;
        }
    }

    public void setYear(short year)
    {
        if(year > 2010 && year < 3000)
        {
            m_year = year;
        }
    }

    public void setHour(byte hour)
    {
        if(hour >= 0 && hour < 24)
        {
            m_hour = hour;
        }
    }

    public void setMinute(byte minute)
    {
        if(minute >= 0 && minute < 60)
        {
            m_minute = minute;
        }
    }

    public java.util.Date getDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, m_year);
        calendar.set(Calendar.MONTH, m_month);
        calendar.set(Calendar.DAY_OF_MONTH, m_day);
        calendar.set(Calendar.HOUR, m_hour);
        calendar.set(Calendar.MINUTE, m_minute);
        return calendar.getTime();
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

    @Override
    public String toString()
    {
        return getDate().toString();
    }


}
