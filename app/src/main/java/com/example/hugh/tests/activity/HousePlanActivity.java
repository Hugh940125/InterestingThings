package com.example.hugh.tests.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugh.tests.R;
import com.example.hugh.tests.Views.CustomTextView;
import com.example.hugh.tests.Views.HousePlanView;

public class HousePlanActivity extends AppCompatActivity implements HousePlanView.OnRoomClickListener{

    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        final HousePlanView hpv = findViewById(R.id.hpv);
        Button bedroom = findViewById(R.id.bedroom);
        Button living_room = findViewById(R.id.living_room);
        Button save = findViewById(R.id.save);
        final Button bt_switch = findViewById(R.id.bt_switch);

        bt_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 0){
                    hpv.setMode(1);
                    mode = 1;
                    bt_switch.setText("显示模式");
                }else {
                    hpv.setMode(0);
                    mode = 0;
                    bt_switch.setText("编辑模式");
                }
            }
        });

        bedroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpv.addRoom("卧室",0.6287,0.2783);
            }
        });

        living_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpv.addRoom("客厅",0.2361,0.3550);
                hpv.addRoom("客厅",0.3213,0.6983);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpv.saveRoomTags();
            }
        });

        hpv.setOnRoomClickListener(this);

        /*CustomTextView customTextView = new CustomTextView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        customTextView.setLayoutParams(layoutParams);
        ll.addView(customTextView);*/
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onRoomClick(View view) {
        String text = ((TextView) view).getText().toString();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
