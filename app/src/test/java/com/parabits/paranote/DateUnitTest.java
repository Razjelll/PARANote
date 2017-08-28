package com.parabits.paranote;

import com.parabits.paranote.data.models.Date;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DateUnitTest {
    @Test
    public void dateFromCodeTest() throws Exception {
        Date date = new Date(108262315);
        assertEquals(2017, date.getYear());
        assertEquals(8, date.getMonth());
        assertEquals(26, date.getDay());
        assertEquals(23, date.getHour());
        assertEquals(15, date.getMinute());
    }

    @Test
    public void dateToCodeTest()
    {
        Date date = new Date(2017, 8, 26, 23, 15);
        int dateCode = date.getCode();
        assertEquals(108262315, dateCode);

        date = new Date(2018, 8, 26, 23, 15);
        dateCode = date.getCode();
        assertEquals(208262315, dateCode);
    }
}