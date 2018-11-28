package com.example.hugh.tests.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by Hugh on 2018/11/26.
 *
 */

public class DensityUtil {

    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float px2dp(float pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().density);
    }

    public static float getScreenHeight() {
        return (Resources.getSystem().getDisplayMetrics().density);
    }
}
