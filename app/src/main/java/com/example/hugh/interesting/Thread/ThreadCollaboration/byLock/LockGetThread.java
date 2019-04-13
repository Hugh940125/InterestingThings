package com.example.hugh.interesting.Thread.ThreadCollaboration.byLock;

import android.util.Log;

/**
 * Created by Hugh on 2019/4/1.
 *
 */

public class LockGetThread extends Thread {

    private final String TAG = "Thread Collaboration";
    private final LockWareHouse mWareHouse;

    public LockGetThread(LockWareHouse wareHouse) {
        this.mWareHouse = wareHouse;
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true){
                if (this.isInterrupted()){
                    throw new InterruptedException();
                }
                sleep(1000);
                mWareHouse.getProduct();
            }
        }catch (InterruptedException e){
            Log.e(TAG,this.getName()+"取出线程中断了");
        }
    }
}
