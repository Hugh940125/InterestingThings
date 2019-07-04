package com.example.hugh.interesting.DesignPattern.BuilderPattern;

import android.util.Log;

/**
 * Created by Hugh on 2019/7/4.
 */
public class Milk implements Packing {
    @Override
    public void pack() {
        Log.e("BuildPattern", "Packing in glass bottle");
    }
}
