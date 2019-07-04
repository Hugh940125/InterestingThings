package com.example.hugh.interesting.DesignPattern.BuilderPattern;

/**
 * Created by Hugh on 2019/7/4.
 */
public interface Product {
    String getName();

    float getPrice();

    Packing packing();
}
