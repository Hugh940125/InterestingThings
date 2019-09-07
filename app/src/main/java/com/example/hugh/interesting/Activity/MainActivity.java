package com.example.hugh.interesting.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hugh.interesting.AnimImageView.AnimActivity;
import com.example.hugh.interesting.Chart.ChartActivity;
import com.example.hugh.interesting.CustomizeViews.CustomImageView.ImageViewActivity;
import com.example.hugh.interesting.CustomizeViews.CustomizeSeekbar.GearSeekBarActivity;
import com.example.hugh.interesting.CustomizeViews.GraphicLock.GraphicLockActivity;
import com.example.hugh.interesting.CustomizeViews.Keyboard.KeyBoardActivity;
import com.example.hugh.interesting.CustomizeViews.ProgressBar.ProgressActivity;
import com.example.hugh.interesting.CustomizeViews.PunchBar.PunchBarActivity;
import com.example.hugh.interesting.CustomizeViews.SmallRedPoint.RedPointActivity;
import com.example.hugh.interesting.EventDelivery.EventDeliveryTestActivity;
import com.example.hugh.interesting.GreenDao.GreenDaoActivity;
import com.example.hugh.interesting.HyperLink.HyperLinkActivity;
import com.example.hugh.interesting.R;
import com.example.hugh.interesting.RecyclerView.RecyclerViewTestActivity;
import com.example.hugh.interesting.Thread.ThreadCollaboration.ThreadActivity;
import com.example.hugh.interesting.ViewBinder.ViewBinderTestActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_header;
    private MainActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        btn_header = findViewById(R.id.btn_header);
        Button svg = findViewById(R.id.svg);
        Button scheme = findViewById(R.id.scheme);
        Button keyboard = findViewById(R.id.keyboard);
        Button red_point = findViewById(R.id.red_point);
        Button animation_image_view = findViewById(R.id.animation_image_view);
        Button view_binder = findViewById(R.id.view_binder);
        Button green_dao = findViewById(R.id.green_dao);
        Button hyper_link = findViewById(R.id.hyper_link);
        Button SeekBar = findViewById(R.id.SeekBar);
        Button graphicLock = findViewById(R.id.graphicLock);
        Button thread = findViewById(R.id.thread);
        Button chart = findViewById(R.id.chart);
        Button surface = findViewById(R.id.surface);
        Button rv = findViewById(R.id.rv);
        Button punch = findViewById(R.id.punch);
        Button imageView = findViewById(R.id.imageView);
        Button event = findViewById(R.id.event);

        event.setOnClickListener(v -> startActivity(new Intent(mActivity, EventDeliveryTestActivity.class)));

        imageView.setOnClickListener(v -> startActivity(new Intent(mActivity, ImageViewActivity.class)));

        punch.setOnClickListener(v -> startActivity(new Intent(mActivity, PunchBarActivity.class)));

        rv.setOnClickListener((view) -> startActivity(new Intent(mActivity, RecyclerViewTestActivity.class)));

        surface.setOnClickListener((view) -> startActivity(new Intent(mActivity, ProgressActivity.class)));

        chart.setOnClickListener(v -> startActivity(new Intent(mActivity, ChartActivity.class)));

        thread.setOnClickListener(v -> startActivity(new Intent(mActivity, ThreadActivity.class)));

        graphicLock.setOnClickListener(v -> startActivity(new Intent(mActivity, GraphicLockActivity.class)));

        SeekBar.setOnClickListener(v -> startActivity(new Intent(mActivity, GearSeekBarActivity.class)));

        hyper_link.setOnClickListener(v -> startActivity(new Intent(mActivity, HyperLinkActivity.class)));

        green_dao.setOnClickListener(v -> startActivity(new Intent(mActivity, GreenDaoActivity.class)));

        view_binder.setOnClickListener(v -> startActivity(new Intent(mActivity, ViewBinderTestActivity.class)));

        animation_image_view.setOnClickListener(v -> startActivity(new Intent(mActivity, AnimActivity.class)));

        scheme.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("url_scheme_b://b_main_activity"));
            startActivity(intent);
        });

        svg.setOnClickListener(v -> startActivity(new Intent(mActivity, SVGActivity.class)));

        keyboard.setOnClickListener(v -> startActivity(new Intent(mActivity, KeyBoardActivity.class)));

        red_point.setOnClickListener(v -> startActivity(new Intent(mActivity, RedPointActivity.class)));

        btn_header.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SVGActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, btn_header, "share").toBundle());
        });
    }
}
