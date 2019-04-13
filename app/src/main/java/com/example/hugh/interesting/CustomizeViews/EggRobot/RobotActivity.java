package com.example.hugh.interesting.CustomizeViews.EggRobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.example.hugh.interesting.CustomizeViews.EggRobot.RobotView;
import com.example.hugh.interesting.R;

public class RobotActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    private RobotView robotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        robotView = findViewById(R.id.rv);
        ViewTreeObserver viewTreeObserver = robotView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        robotView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        robotView.Appearance();
    }
}
