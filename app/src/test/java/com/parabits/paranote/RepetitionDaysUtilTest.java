package com.parabits.paranote;

import com.parabits.paranote.utils.RepetitionDaysUtils;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class RepetitionDaysUtilTest {

    @Test
    public void getPatternTest()
    {
        String testPatternString = "1;5;7;12";
        List<Integer> correctResultList = new ArrayList<>();
        correctResultList.add(1);
        correctResultList.add(5);
        correctResultList.add(7);
        correctResultList.add(12);

        List<Integer> resultList = RepetitionDaysUtils.getPattern(testPatternString);
        assertEquals(correctResultList.size(), resultList.size());
        assertEquals(correctResultList.get(0), resultList.get(0));
        assertEquals(correctResultList.get(1), resultList.get(1));
        assertEquals(correctResultList.get(2), resultList.get(2));
        assertEquals(correctResultList.get(3), resultList.get(3));
    }

    @Test
    public void getPatternStringTest()
    {
        List<Integer> testPattern = new ArrayList<>();
        testPattern.add(1);
        testPattern.add(5);
        testPattern.add(7);
        testPattern.add(12);

        String correctPatternString = "1;5;7;12";

        String resultString = RepetitionDaysUtils.getPatternString(testPattern);
        assertEquals(correctPatternString, resultString);
    }

    @Test
    public void getPatternStringFromEmptyTest()
    {
        List<Integer> testPattern = new ArrayList<>();
        String correctPatternString = "";
        String resultString = RepetitionDaysUtils.getPatternString(testPattern);
        assertEquals(correctPatternString, resultString);
    }
}
