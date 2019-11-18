package com.example.hugh.interesting.Utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Hugh on 2019/11/4.
 */
public class DisplayUtils {

    public static int getScreenWidth(Activity activity){
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
