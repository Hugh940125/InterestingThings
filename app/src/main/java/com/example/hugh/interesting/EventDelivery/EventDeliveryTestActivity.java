package com.example.hugh.interesting.EventDelivery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugh.interesting.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDeliveryTestActivity extends AppCompatActivity {

    @BindView(R.id.tv_square)
    TestTextView tvSquare;
    @BindView(R.id.rl_square)
    TestRelativeLayout rlSquare;
    private String TAG = "EVENT_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_delivery);
        ButterKnife.bind(this);

        rlSquare.setOnClickListener(v -> Toast.makeText(EventDeliveryTestActivity.this, "红色点击", Toast.LENGTH_SHORT).show());
        tvSquare.setOnClickListener(v -> Toast.makeText(EventDeliveryTestActivity.this, "蓝色点击", Toast.LENGTH_SHORT).show());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent ---- EventDeliveryTestActivity");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent ---- EventDeliveryTestActivity");
        return super.dispatchTouchEvent(ev);
    }
}
