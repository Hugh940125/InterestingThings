package com.example.hugh.interesting.Thread.ThreadCollaboration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hugh.interesting.R;
import com.example.hugh.interesting.Thread.ThreadCollaboration.byLock.LockGetThread;
import com.example.hugh.interesting.Thread.ThreadCollaboration.byLock.LockPutThread;
import com.example.hugh.interesting.Thread.ThreadCollaboration.byLock.LockWareHouse;
import com.example.hugh.interesting.Thread.ThreadCollaboration.bySynchronized.GetThread;
import com.example.hugh.interesting.Thread.ThreadCollaboration.bySynchronized.PutThread;
import com.example.hugh.interesting.Thread.ThreadCollaboration.bySynchronized.WareHouse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThreadActivity extends AppCompatActivity {

//    @BindView(R.id.bt_tc)
//    Button btTc;
//    @BindView(R.id.bt_stop_tc)
//    Button btStopTc;
//    @BindView(R.id.bt_lock)
//    Button btLock;
//    @BindView(R.id.bt_stop_lock)
//    Button btStopLock;
    private ArrayList<Thread> threadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tc);
        ButterKnife.bind(this);
        threadList = new ArrayList<>();
    }

    /*@OnClick({R.id.bt_tc, R.id.bt_stop_tc,R.id.bt_lock, R.id.bt_stop_lock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_tc:
                threadList.clear();
                WareHouse wareHouse = new WareHouse();
                for (int i = 0; i < 10; i++) {
                    Thread putThread = new Thread(new PutThread(wareHouse));
                    putThread.start();
                    threadList.add(putThread);
                    Thread getThread = new Thread(new GetThread(wareHouse));
                    getThread.start();
                    threadList.add(getThread);
                }
                break;
            case R.id.bt_stop_tc:
                for (Thread thread : threadList) {
                    thread.interrupt();
                }
                break;
            case R.id.bt_lock:
                threadList.clear();
                LockWareHouse lockWareHouse = new LockWareHouse();
                for (int i = 0; i < 10; i++) {
                    Thread putThread = new Thread(new LockPutThread(lockWareHouse));
                    putThread.start();
                    threadList.add(putThread);
                    Thread getThread = new Thread(new LockGetThread(lockWareHouse));
                    getThread.start();
                    threadList.add(getThread);
                }
                break;
            case R.id.bt_stop_lock:
                for (Thread thread : threadList) {
                    thread.interrupt();
                }
                break;
        }
    }*/
}
