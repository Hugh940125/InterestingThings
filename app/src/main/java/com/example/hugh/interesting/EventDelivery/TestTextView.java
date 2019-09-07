package com.example.hugh.interesting.EventDelivery;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Hugh on 2019/7/18.
 */
public class TestTextView extends android.support.v7.widget.AppCompatTextView {

    private String TAG = "EVENT_TEST";

    public TestTextView(Context context) {
        super(context);
    }

    public TestTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent ---- TestTextView");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "dispatchTouchEvent ---- TestTextView");
        return super.dispatchTouchEvent(event);
    }
}
