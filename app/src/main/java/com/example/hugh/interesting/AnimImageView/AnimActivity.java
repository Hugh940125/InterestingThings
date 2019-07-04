package com.example.hugh.interesting.AnimImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hugh.interesting.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimActivity extends Activity {

    @BindView(R.id.iv_anim)
    ImageView ivAnim;
    @BindView(R.id.bt)
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        ButterKnife.bind(this);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        final AnimImageView animImageView = new AnimImageView();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.robot_telescopic_001);
        list.add(R.drawable.robot_telescopic_002);
        list.add(R.drawable.robot_telescopic_003);
        list.add(R.drawable.robot_telescopic_004);
        list.add(R.drawable.robot_telescopic_005);
        list.add(R.drawable.robot_telescopic_006);
        list.add(R.drawable.robot_telescopic_007);
        list.add(R.drawable.robot_telescopic_008);
        list.add(R.drawable.robot_telescopic_009);
        list.add(R.drawable.robot_telescopic_010);
        list.add(R.drawable.robot_telescopic_011);
        list.add(R.drawable.robot_telescopic_012);
        list.add(R.drawable.robot_telescopic_013);
        animImageView.setAnimation(ivAnim, list);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animImageView.start(true,100);
            }
        });
    }
}
