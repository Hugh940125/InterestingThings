package com.example.hugh.interesting.CustomizeViews.CustomizeSeekbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.example.hugh.interesting.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GearSeekBarActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    @BindView(R.id.gs)
    GearSeekBar gs;
    @BindView(R.id.cs)
    CircleSeekBar cs;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.ac)
    AirControl ac;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
        ButterKnife.bind(this);

        cs.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //允许ScrollView截断点击事件，ScrollView可滑动
                sv.requestDisallowInterceptTouchEvent(false);
            } else {
                //不允许ScrollView截断点击事件，点击事件由子View处理
                sv.requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });

//        ac.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                //允许ScrollView截断点击事件，ScrollView可滑动
//                sv.requestDisallowInterceptTouchEvent(false);
//            } else {
//                //不允许ScrollView截断点击事件，点击事件由子View处理
//                sv.requestDisallowInterceptTouchEvent(true);
//            }
//            return false;
//        });

        ac.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        ac.setData(32,null,null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ac.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }
}
