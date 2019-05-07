package com.example.hugh.interesting.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hugh.interesting.Chart.ChartActivity;
import com.example.hugh.interesting.CoordinatorLayout.CoordinatorActivity;
import com.example.hugh.interesting.CustomizeViews.CustomizeSeekbar.SeekBarActivity;
import com.example.hugh.interesting.CustomizeViews.EggRobot.RobotActivity;
import com.example.hugh.interesting.CustomizeViews.GraphicLock.GraphicLockActivity;
import com.example.hugh.interesting.GreenDao.GreenDaoActivity;
import com.example.hugh.interesting.HyperLink.HyperLinkActivity;
import com.example.hugh.interesting.R;
import com.example.hugh.interesting.Anim.AnimActivity;
import com.example.hugh.interesting.CustomizeViews.HousePlan.HousePlanActivity;
import com.example.hugh.interesting.CustomizeViews.Keyboard.KeyBoardActivity;
import com.example.hugh.interesting.CustomizeViews.SmallRedPoint.RedPointActivity;
import com.example.hugh.interesting.Thread.ThreadCollaboration.ThreadActivity;
import com.example.hugh.interesting.ViewBinder.ViewBinderTestActivity;

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
        Button scheme = findViewById(R.id.scheme);
        Button keyboard = findViewById(R.id.keyboard);
        Button red_point = findViewById(R.id.red_point);
        Button animation_image_view = findViewById(R.id.animation_image_view);
        Button view_binder = findViewById(R.id.view_binder);
        Button coordinator = findViewById(R.id.coordinator);
        Button green_dao = findViewById(R.id.green_dao);
        Button hyper_link = findViewById(R.id.hyper_link);
        Button SeekBar = findViewById(R.id.SeekBar);
        Button graphicLock = findViewById(R.id.graphicLock);
        Button thread = findViewById(R.id.thread);
        Button chart = findViewById(R.id.chart);

        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, ChartActivity.class));
            }
        });

        thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,ThreadActivity.class));
            }
        });

        graphicLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,GraphicLockActivity.class));
            }
        });

        SeekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,SeekBarActivity.class));
            }
        });

        hyper_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,HyperLinkActivity.class));
            }
        });

        green_dao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,GreenDaoActivity.class));
            }
        });

        coordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,CoordinatorActivity.class));
            }
        });

        view_binder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,ViewBinderTestActivity.class));
            }
        });

        animation_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,AnimActivity.class));
            }
        });

        scheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("url_scheme_b://b_main_activity"));
                startActivity(intent);
            }
        });

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

        keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,KeyBoardActivity.class));
            }
        });

        red_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,RedPointActivity.class));
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
