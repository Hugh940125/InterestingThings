package com.example.hugh.interesting.Thread.ThreadCollaboration.byLock;

import android.util.Log;

/**
 * Created by Hugh on 2019/4/1.
 *
 */

public class LockPutThread extends Thread {

    private final String TAG = "Thread Collaboration";
    private final LockWareHouse mWareHouse;

    public LockPutThread(LockWareHouse wareHouse) {
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
                mWareHouse.putProduct();
            }
        }catch (InterruptedException e){
            Log.e(TAG,this.getName()+"放入线程中断了");
        }
    }
}
