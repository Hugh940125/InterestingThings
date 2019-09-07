package com.example.hugh.interesting.Application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;

import com.example.hugh.interesting.GreenDao.db.DaoMaster;
import com.example.hugh.interesting.GreenDao.db.DaoSession;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hugh on 2019/3/5.
 *
 */

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.OpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "hugh.db");
        SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }
}
