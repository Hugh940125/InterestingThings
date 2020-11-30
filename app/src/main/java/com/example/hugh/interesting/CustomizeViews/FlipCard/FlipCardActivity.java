package com.example.hugh.interesting.CustomizeViews.FlipCard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;

import com.example.hugh.interesting.CustomizeViews.FlipCard.notice.NoticeView;
import com.example.hugh.interesting.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlipCardActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    @BindView(R.id.fv_notice)
    FlipCardVIew fvNotice;
    @BindView(R.id.nv)
    NoticeView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_card);
        ButterKnife.bind(this);
        ViewTreeObserver viewTreeObserver = fvNotice.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this);

        List<String> notices = new ArrayList<>();
        notices.add("大促销下单拆福袋，亿万新年红包随便拿");
        notices.add("家电五折团，抢十亿无门槛现金红包");
        notices.add("星球大战剃须刀首发送200元代金券");
        notices.add("1");
        notices.add("2");
        notices.add("3");
        nv.addNotice(notices);
        nv.startFlipping();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fvNotice.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        fvNotice.start(true);
        fvNotice.setOnFlipListener(() -> {

        });
    }
}
