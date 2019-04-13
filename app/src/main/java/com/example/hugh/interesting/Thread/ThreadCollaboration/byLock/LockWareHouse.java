package com.example.hugh.interesting.Thread.ThreadCollaboration.byLock;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Hugh on 2019/4/1.
 *
 */

public class LockWareHouse {

    private final String TAG = "Thread Collaboration";
    private int num = 0;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void getProduct(){
        readWriteLock.readLock().lock();
        try{
            while (num > 0){
                num--;
                Log.e(TAG,"取出物品"+num+"--"+Thread.currentThread().getName());
            }
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void putProduct(){
        readWriteLock.writeLock().lock();
        try{
            while (num<=5){
                num++;
                Log.e(TAG,"放入物品"+num+"--"+Thread.currentThread().getName());
            }
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
