package com.example.hugh.tests.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.example.hugh.tests.R;

/**
 * Created by Hugh on 2018/11/8.
 *
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    private int mode;
    private Paint mPaint;
    private int width;
    private int height;


    public CustomTextView(Context context,int mode) {
        super(context);
        this.mode = mode;
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.colorAccent));
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setMode(int mode){
        this.mode = mode;
        invalidate();
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (widthMode){
            case MeasureSpec.EXACTLY:
                width = MeasureSpec.getSize(widthMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                width = 500;
                break;
        }
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                height = 100;
                break;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mode == 0){
            drawDeleteRegion(canvas);
        }
    }

    private void drawDeleteRegion(Canvas canvas) {
        //这里的参数是相对自己的，不能使用，getLeft,getTop...
        Rect rect = new Rect(getMeasuredWidth() - 30, 0, getMeasuredWidth(), 30);
        canvas.drawRect(rect, mPaint);
    }
}
