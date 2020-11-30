package com.example.hugh.interesting.Thread.ThreadCollaboration.bySynchronized;

import android.util.Log;

/**
 * Created by Hugh on 2019/4/1.
 */

public class GetThread extends Thread {

    private final WareHouse mWareHouse;

    public GetThread(WareHouse wareHouse,String name) {
        this.mWareHouse = wareHouse;
        setName(name);
    }

    @Override
    public void run() {
        super.run();
        try {
            if (this.isInterrupted()) {
                throw new InterruptedException();
            }
            sleep(1000);
            mWareHouse.getProduct();
        } catch (InterruptedException e) {
            String TAG = "Thread Collaboration";
            Log.e(TAG, this.getName() + "取出线程中断了");
        }
    }
}
