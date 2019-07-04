package com.example.hugh.interesting.AnimImageView;

/**
 * Created by Hugh on 2019/1/9.
 *
 */

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AnimImageView {
    private static final int MSG_START = 0xf1;
    private static final int MSG_STOP  = 0xf2;
    private static final int STATE_STOP = 0xf3;
    private static final int STATE_RUNNING = 0xf4;

    private int mState = STATE_RUNNING;
    private ImageView mImageView;
    private List<Integer> mResourceIdList = null;
    private Timer mTimer = null;
    private AnimTimerTask mTimeTask = null;
    private int mFrameIndex = 0;
    private boolean isLooping = false;

    public AnimImageView(){
        mTimer = new Timer();
    }

    public void setAnimation(ImageView imageview, List<Integer> resourceIdList ){
        mImageView = imageview;
        mResourceIdList = resourceIdList;
        mImageView.setImageResource(mResourceIdList.get(0));
    }

    public void start(boolean loop, int duration){
        stop();
        isLooping = loop;
        mFrameIndex = 0;
        mState = STATE_RUNNING;
        mTimeTask = new AnimTimerTask();
        mTimer.schedule(mTimeTask, 0, duration);
    }

    public void stop(){
        if (mTimeTask != null) {
            mFrameIndex = 0;
            mState = STATE_STOP;
            mTimer.purge();
            mTimeTask.cancel();
            mTimeTask = null;
            mImageView.setBackgroundResource(mResourceIdList.get(mResourceIdList.size() - 1));
        }
    }

    class AnimTimerTask extends TimerTask {
        @Override
        public void run() {
            if(mFrameIndex < 0 || mState == STATE_STOP){
                return;
            }

            if( mFrameIndex < mResourceIdList.size() ){
                Message msg = AnimHandler.obtainMessage(MSG_START,0,0,null);
                msg.sendToTarget();
            }else{
                mFrameIndex = 0;
                if(!isLooping){
                    Message msg = AnimHandler.obtainMessage(MSG_STOP,0,0,null);
                    msg.sendToTarget();
                }
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler AnimHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_START:{
                    if(mFrameIndex >=0 && mFrameIndex < mResourceIdList.size() && mState == STATE_RUNNING){
                        mImageView.setImageResource(mResourceIdList.get(mFrameIndex));
                        mFrameIndex++;
                    }
                }
                break;
                case MSG_STOP:{
                    if (mTimeTask != null) {
                        mFrameIndex = 0;
                        mTimer.purge();
                        mTimeTask.cancel();
                        mState = STATE_STOP;
                        mTimeTask = null;
                        mImageView.setImageResource(mResourceIdList.get(mResourceIdList.size() - 1));
                    }
                }
                break;
                default:
                    break;
            }
        }
    };
}
