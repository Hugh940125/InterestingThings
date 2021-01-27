package com.example.hugh.interesting.sp_util;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hugh on 2016/8/8.
 * Save  data to SharePreferences
 */
public class SP_Utils {

    public static String PREFERENCE_NAME = "TrineaAndroidCommon";

    /**
     * put int preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * put string preferences
     *
     * @param context
     * @param key The name of the preference to modify
     *
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     *  put Arraylist preference
     *
     *  @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a arrraylist
     */
    public static void putStrListValue(Context context, String key, List<String> strList) {
        if (null == strList) {
            return;
        }
        //remove data before save to keep the data unique
        removeStrList(context, key);
        int size = strList.size();
        putInt(context, key + "size", size);
        for (int i = 0; i < size; i++) {
            putString(context, key + i, strList.get(i));
        }
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a string
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * get List<String>
     *
     * @param context
     * @param key
     *            List<String> 对应的key
     * @return List<String>
     */
    public static List<String> getStrListValue(Context context, String key) {
        List<String> strList = new ArrayList<String>();
        int size = getIntValue(context, key + "size", 0);
        //Log.d("sp", "" + size);
        for (int i = 0; i < size; i++) {
            strList.add(getString(context, key + i, null));
        }
        return strList;
    }

    /**
     * clear List<String>
     *
     * @param context
     * @param key  key of List<String>
     */
    public static void removeStrList(Context context, String key) {
        int size = getIntValue(context, key + "size", 0);
        if (0 == size) {
            return;
        }
        remove(context, key + "size");
        for (int i = 0; i < size; i++) {
            remove(context, key + i);
        }
    }

    /**
     * get data（int)
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    private static int getIntValue(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        int value = sp.getInt(key, defValue);
        return value;
    }

    /**
     * clear data of key
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit();
        sp.remove(key);
        sp.commit();
    }

    /**
     * put map preference
     * @param key the name of map preference
     * @param map the value of map preference
     */
    public static void putMapValue(Context context,String key,HashMap<String, String> map){
        ArrayList<String> strings = new ArrayList<>();
        strings.clear();
        for (String set_key:map.keySet()){
            strings.add(set_key);
            strings.add(map.get(set_key));
        }
        putStrListValue(context,key,strings);
    }

    /**
     * get map preference
     * @param key the name of the map saved before
     */
    public static HashMap getMapValue(Context context,String key){
        List<String> listFromSp = getStrListValue(context, key);
        HashMap<String, String> HashMap = new HashMap<>();
        for (int i=0;i<listFromSp.size();i=i+2){
            String s = listFromSp.get(i);
            String s1 = listFromSp.get(i + 1);
            HashMap.put(s,s1);
        }
        return HashMap;
    }
}
