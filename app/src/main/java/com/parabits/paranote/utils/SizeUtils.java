package com.parabits.paranote.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;


public class SizeUtils {

    public static float getDp(int px, Context context)
    {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, displayMetrics);
    }

    public static float getPx(int dp, Context context)
    {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (dp * context.getResources().getSystem().getDisplayMetrics().density);
    }
}
