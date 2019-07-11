package com.example.hugh.interesting.DesignPattern.AdapterPattern;

/**
 * Created by Hugh on 2019/7/4.
 */
public class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void playMp4(String fileName) {
        System.out.print("----->playMp4:"+fileName);
    }

    @Override
    public void playAvi(String fileName) {

    }
}
