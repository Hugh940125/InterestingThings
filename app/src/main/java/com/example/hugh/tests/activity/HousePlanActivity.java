package com.example.hugh.tests.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugh.tests.R;
import com.example.hugh.tests.views.HousePlanView;

public class HousePlanActivity extends AppCompatActivity implements HousePlanView.OnTagClickListener,HousePlanView.OnTagDeleteListener,HousePlanView.OnTagRecyclingListener{

    private int mode;
    private AlertDialog show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_plan);
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

        hpv.setOnTagClickListener(this);
        hpv.setOnTagDeleteListener(this);
        hpv.setOnTagRecyclingListener(this);

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
    public void onTagClick(View view) {
        String text = ((TextView) view).getText().toString();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnTagDelete(final View view, final TagDeleteCallback tagDeleteCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否删除？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tagDeleteCallback.onConfirm(view);
                show.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tagDeleteCallback.onCancel();
                show.dismiss();
            }
        });
        show = builder.create();
        show.show();
    }

    @Override
    public void OnTagRecycling(View view, TagRecyclingCallback tagRecyclingCallback) {
        tagRecyclingCallback.onConfirm(view);
    }
}
