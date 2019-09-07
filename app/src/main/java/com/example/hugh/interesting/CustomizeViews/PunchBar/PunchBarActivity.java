package com.example.hugh.interesting.CustomizeViews.PunchBar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.hugh.interesting.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PunchBarActivity extends AppCompatActivity {

    @BindView(R.id.rl_scroll)
    RelativeLayout rlScroll;
    private float x = 0F;
    private float y = 0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_bar);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                rlScroll.scrollTo((int)(x -x1), (int)(y -y1));
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
//        return super.onTouchEvent(event);
    }
}
