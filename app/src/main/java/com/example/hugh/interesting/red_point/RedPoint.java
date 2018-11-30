package com.example.hugh.interesting.red_point;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.hugh.interesting.utils.DensityUtil;

/**
 * Created by Hugh on 2018/11/30.
 *
 */

public class RedPoint extends View {
    private Context mContext;
    private int mViewHeight;
    private int mViewWidth;
    private Paint mPaint;

    public RedPoint(Context context) {
        super(context);
        init(context);
    }

    public RedPoint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = 0;
        int heightSize = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY){
            widthSize = MeasureSpec.getSize(widthMeasureSpec);
        }else if (widthMode == MeasureSpec.AT_MOST){
            widthSize = DensityUtil.dp2px(200);
        }
        if (heightMode == MeasureSpec.EXACTLY){
            heightSize = MeasureSpec.getSize(heightMeasureSpec);
        }else if (widthMode == MeasureSpec.AT_MOST){
            heightSize = DensityUtil.dp2px(200);
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewHeight = getHeight();
        mViewWidth = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawCircle(mViewWidth/2,mViewHeight/2,30,mPaint);
        Path path = new Path();
        path.addCircle(mViewWidth/2,mViewHeight/2,30, Path.Direction.CW);
        canvas.drawPath(path,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
