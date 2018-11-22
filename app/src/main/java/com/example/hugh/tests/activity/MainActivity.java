package com.example.hugh.tests.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hugh.tests.R;

public class MainActivity extends AppCompatActivity {

    private TextView tv_header;
    private ScrollView sv_root;
    private MainActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

        tv_header = findViewById(R.id.tv_header);
        sv_root = findViewById(R.id.sv_root);
        Button robot = findViewById(R.id.robot);
        Button svg = findViewById(R.id.svg);
        Button house_plan = findViewById(R.id.house_plan);

        robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,RobotActivity.class));
            }
        });

        svg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,SVGActivity.class));
            }
        });

        house_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,HousePlanActivity.class));
            }
        });

        tv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SVGActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, tv_header, "share").toBundle());
                }
            }
        });
    }
}
