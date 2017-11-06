package com.example.hugh.tests.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;
import android.widget.TextView;

public class ScrollView extends TextView {

    private int mLastX;
    private int mLastY;
    private Scroller scroller;

    public ScrollView(Context context) {
        super(context);
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = rawX - mLastX;
                int deltaY = rawY - mLastY;
                scroller.startScroll(mLastX,mLastY,deltaX,deltaY,1000);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = rawX;
        mLastY = rawY;
        return true;
    }
}