package com.example.hugh.interesting.Chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.service.autofill.FillCallback;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.hugh.interesting.Utils.DensityUtil;

/**
 * Created by Hugh on 2018/12/7.
 *
 */

public class LineChart extends View {

    private Paint mAxisPaint;
    private int mXStep = 1;
    private int mYStep = 1;
    private int mXMax = 100;
    private int mYMax = 100;
    private int mXCells = 10;
    private int mYCells = 10;

    public LineChart(Context context) {
        this(context,null);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mAxisPaint = new Paint();
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setColor(Color.BLACK);
        mAxisPaint.setStyle(Paint.Style.FILL);
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
            widthSize = DensityUtil.dp2px(500);
        }
        if (heightMode == MeasureSpec.EXACTLY){
            heightSize = MeasureSpec.getSize(heightMeasureSpec);
        }else if (widthMode == MeasureSpec.AT_MOST){
            heightSize = DensityUtil.dp2px(300);
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    public void setAttrs(int x_step,int y_step,int x_max,int y_max){
        this.mXStep = x_step;
        this.mYStep = y_step;
        this.mXMax = x_max;
        this.mYMax = y_max;
    }

    private void drawAxis(Canvas canvas){
        int height = getHeight();
        int width = getWidth();
        canvas.drawLine(DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(16),getRight()-DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(16),mAxisPaint);//横坐标
        canvas.drawLine(DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(16),DensityUtil.dp2px(16),getTop()+DensityUtil.dp2px(16),mAxisPaint);//纵坐标
        int mXStep = (width-DensityUtil.dp2px(40)) / mXCells;
        int mYStep = (height-DensityUtil.dp2px(40)) / mYCells;
        for (int i=0;i<=mXCells;i++){
            canvas.drawLine(mXStep*i+DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(16),mXStep*i+DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(20),mAxisPaint);
        }
        for (int i=0;i<=mYCells;i++){
            canvas.drawLine(DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(16)-mYStep*i,DensityUtil.dp2px(20),getBottom()-DensityUtil.dp2px(16)-mYStep*i,mAxisPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAxis(canvas);
    }
}
