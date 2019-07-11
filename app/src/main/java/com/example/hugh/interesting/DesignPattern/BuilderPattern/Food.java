package com.example.hugh.interesting.DesignPattern.BuilderPattern;

/**
 * Created by Hugh on 2019/7/4.
 */
public abstract class Food implements Product {

    @Override
    public Packing packing() {
        return new PlasticBag();
    }
}
