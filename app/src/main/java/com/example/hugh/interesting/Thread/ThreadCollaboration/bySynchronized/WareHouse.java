package com.example.hugh.interesting.Thread.ThreadCollaboration.bySynchronized;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugh on 2019/4/1.
 *
 */

public class WareHouse {

    private final String TAG = "Thread Collaboration";
    private List<Product> mProducts = new ArrayList<>();
    //wait和notify方法必须工作于synchronized内部，且这两个方法只能由锁对象来调用
    public synchronized Product getProduct(){
        while (mProducts.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Product product = mProducts.get(0);
        mProducts.clear();
        notifyAll();
        Log.e(TAG,"取出物品"+Thread.currentThread().getName());
        return product;
    }

    public synchronized void putProduct(Product product){
        while (mProducts.size() > 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mProducts.add(product);
        notifyAll();
        Log.e(TAG,"放入物品"+Thread.currentThread().getName());
    }
}
