package com.example.hugh.interesting.DesignPattern.BuilderPattern;


/**
 * Created by Hugh on 2019/7/4.
 */
public class Bread extends Food {
    @Override
    public String getName() {
        return "Bread";
    }

    @Override
    public float getPrice() {
        return 3.5F;
    }
}
