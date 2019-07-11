package com.example.hugh.interesting.DesignPattern.AdapterPattern;

/**
 * Created by Hugh on 2019/7/4.
 */
public class PlayerAdapter implements MediaPlayer {

    private AdvancedMediaPlayer mAdapter;

    public PlayerAdapter(String type) {
        if ("Mp4".equals(type)){
            mAdapter = new Mp4Player();
        }else if ("Avi".equals(type)){
            mAdapter = new AviPlayer();
        }
    }

    @Override
    public void play(String type, String fileName) {
        if ("Mp4".equals(type)){
            mAdapter.playMp4(fileName);
        }else if ("Avi".equals(type)){
            mAdapter.playAvi(fileName);
        }
    }
}
