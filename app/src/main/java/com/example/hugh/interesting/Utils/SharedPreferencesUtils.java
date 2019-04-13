package com.example.hugh.interesting.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * @Author : Zoro.
 * @Date : 2018/7/25.
 * @Describe :  extPlatform:平台
 */

public class SharedPreferencesUtils {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Context mContext;
    private static SharedPreferencesUtils instance;

    public static SharedPreferencesUtils getInstance() {
        if (null == instance) {
            instance = new SharedPreferencesUtils();
        }
        checkEvn();
        return instance;
    }

    private static void checkEvn() {
        if (null == mContext) {
            return;
        }
        if (null == sharedPreferences) {
            sharedPreferences = mContext.getSharedPreferences("ZJY",
                    Context.MODE_PRIVATE);
        } else {
        }
        if (null == editor) {
            editor = sharedPreferences.edit();
        } else {
        }
    }


    public static void init(Context context) {
        mContext = context;
    }

    public void putStringParam(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void putBooleanParam(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putIntegerParam(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public String getStringParam(String key) {
        return sharedPreferences.getString(key, "");
    }

    public String getStringParam(String key,String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public Integer getIntegerParam(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public Integer getIntegerParam(String key,Integer defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public boolean getBooleanParam(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public boolean getBooleanParam(String key,Boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void removeOne(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clearAll() {
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否存在
     */
    public Boolean contain(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }
}
