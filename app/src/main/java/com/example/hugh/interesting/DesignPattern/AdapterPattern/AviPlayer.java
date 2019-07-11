package com.example.hugh.interesting.DesignPattern.AdapterPattern;

/**
 * Created by Hugh on 2019/7/4.
 */
public class AviPlayer implements AdvancedMediaPlayer {
    @Override
    public void playMp4(String fileName) {

    }

    @Override
    public void playAvi(String fileName) {
        System.out.print("----->playAvi:"+fileName);
    }
}
