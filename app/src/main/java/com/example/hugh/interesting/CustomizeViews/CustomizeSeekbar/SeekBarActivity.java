package com.example.hugh.interesting.CustomizeViews.CustomizeSeekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hugh.interesting.R;

public class SeekBarActivity extends AppCompatActivity {

    private CustomizeSeekBar cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
        cs = findViewById(R.id.cs);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                cs.setPosition(3);
//            }
//        },1000);

    }
}
