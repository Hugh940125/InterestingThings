package com.example.hugh.tests;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_header;
    private ListView lv_content;
    private ScrollView sv_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_header = (TextView) findViewById(R.id.tv_header);
        lv_content = (ListView) findViewById(R.id.lv_content);
        sv_root = (ScrollView) findViewById(R.id.sv_root);

        tv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SVGActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,tv_header,"share").toBundle());
                }
            }
        });

        sv_root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float y = motionEvent.getY();
                        Log.e("当前的y",y+"");
                        if (y >= 100){
                            sv_root.setEnabled(false);
                        }else {
                            sv_root.setEnabled(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });

        /*//属性动画的简写方式
        tv_header.animate()
                .alpha(0)
                .setDuration(3000)
                .y(300)
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {

                    }
                })
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }).start();*/
    }

    /*//取消线程
    private volatile Thread myThread;

    //1.在onstop方法中停止线程
    @Override
    protected void onStop() {
        super.onStop();
        Thread dummy = myThread;
        myThread = null;
        dummy.interrupt();
    }

    //2.将线程设置为守护线程，使线程在主线程停止时停止
    //myThread.setDeamon(true);
    //myThread.start();*/

}
