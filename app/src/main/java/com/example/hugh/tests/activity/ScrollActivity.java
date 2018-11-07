package com.example.hugh.tests.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hugh.tests.R;
import com.example.hugh.tests.Views.HousePlanView;

public class ScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        final HousePlanView hpv = findViewById(R.id.hpv);
        Button bedroom = findViewById(R.id.bedroom);
        Button living_room = findViewById(R.id.living_room);
        Button save = findViewById(R.id.save);
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

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
