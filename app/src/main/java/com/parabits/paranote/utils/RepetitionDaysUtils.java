package com.parabits.paranote.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Razjelll on 10.09.2017.
 */

public class RepetitionDaysUtils {

    private static String PATTERN_SEPARATOR = ";";

    public static String getPatternString(List<Integer> days)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i< days.size(); i++)
        {
            stringBuilder.append(days.get(i));
            if(i != days.size() -1)
            {
                stringBuilder.append(PATTERN_SEPARATOR);
            }
        }
        return stringBuilder.toString();
    }

    public static List<Integer> getPattern(String patternString)
    {
        List<Integer> resultList = new ArrayList<>();
        if(patternString == null) return resultList;
        StringTokenizer tokenizer = new StringTokenizer(patternString, PATTERN_SEPARATOR);
        while(tokenizer.hasMoreElements())
        {
            resultList.add(Integer.parseInt(tokenizer.nextToken()));
        }
        return resultList;
    }

}
