package com.example.hugh.interesting.view_binder;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Hugh on 2019/1/23.
 *
 */

public class RunTimeViewBinder {

    public static void bind(Context context){
        if (context instanceof Activity){
            Field[] declaredFields = context.getClass().getDeclaredFields();
            for (int i=0;i<declaredFields.length;i++){
                if (declaredFields[i].isAnnotationPresent(RunTimeViewIdBinder.class)){
                    int viewId = declaredFields[i].getAnnotation(RunTimeViewIdBinder.class).value();
                    declaredFields[i].setAccessible(true);
                    try {
                        declaredFields[i].set(context,((Activity) context).findViewById(viewId));
                        declaredFields[i].setAccessible(false);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        declaredFields[i].setAccessible(false);
                    }
                }
            }
        }else {
            //Log.e()
        }
    }

}
