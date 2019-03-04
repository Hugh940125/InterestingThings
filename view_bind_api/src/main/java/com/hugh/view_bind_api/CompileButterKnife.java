package com.hugh.view_bind_api;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Hugh on 2019/3/4.
 *
 */

public class CompileButterKnife {
    public static void bind(Activity target) {
        View sourceView = target.getWindow().getDecorView();
        createBinding(target, sourceView);
    }

    private static void createBinding(Activity target, View source) {
        Class<?> targetClass = target.getClass();
        String className = targetClass.getName();
        try {
            Class<?> bindingClass = targetClass.getClassLoader().loadClass(className + "_ViewBinding");
            Constructor constructor = bindingClass.getConstructor(targetClass, View.class);
            constructor.newInstance(target, source);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}