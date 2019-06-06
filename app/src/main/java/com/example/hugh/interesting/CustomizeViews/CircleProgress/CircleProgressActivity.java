package com.example.hugh.interesting.CustomizeViews.CircleProgress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.hugh.interesting.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CircleProgressActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.cpb)
    CircleProgressBar cpb;
    private ViewTreeObserver viewTreeObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress);
        ButterKnife.bind(this);

        viewTreeObserver = cpb.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this);
    }

    @OnClick(R.id.start)
    public void onViewClicked() {
        cpb.start();
    }

    @Override
    public void onGlobalLayout() {
        cpb.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewTreeObserver.removeOnGlobalLayoutListener(this);
    }
}
