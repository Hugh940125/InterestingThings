package com.example.hugh.interesting.CustomizeViews.EggRobot;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Hugh on 2018/12/14.
 *
 */

public class TimerHandler extends Handler {
    private RobotView mRobotView;

    public TimerHandler(RobotView robotView) {
        this.mRobotView = robotView;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case 10086:
                mRobotView.storedRobot();
                break;
            default:
                super.handleMessage(msg);
                break;
        }
    }
}
