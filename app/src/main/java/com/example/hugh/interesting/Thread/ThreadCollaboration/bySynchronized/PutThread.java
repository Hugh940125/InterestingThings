package com.example.hugh.interesting.Thread.ThreadCollaboration.bySynchronized;

import android.util.Log;

/**
 * Created by Hugh on 2019/4/1.
 *
 */

public class PutThread extends Thread {

    private final String TAG = "Thread Collaboration";
    private final WareHouse mWareHouse;

    public PutThread(WareHouse wareHouse) {
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
                mWareHouse.putProduct(new Product());
            }
        }catch (InterruptedException e){
            Log.e(TAG,this.getName()+"放入线程中断了");
        }
    }
}
