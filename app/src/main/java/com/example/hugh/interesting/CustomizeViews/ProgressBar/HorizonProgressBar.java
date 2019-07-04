package com.example.hugh.interesting.CustomizeViews.ProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.hugh.interesting.Utils.DensityUtil;

/**
 * Created by Hugh on 2019/6/9.
 */
public class HorizonProgressBar extends View {

    private Paint mProgressPaint;
    private Paint mBGPaint;
    private float mPercent = 0.55F;

    public HorizonProgressBar(Context context) {
        this(context, null);
    }

    public HorizonProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setStrokeWidth(DensityUtil.dp2px(10));
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setColor(Color.parseColor("#3092ea"));

        mBGPaint = new Paint();
        mBGPaint.setAntiAlias(true);
        mBGPaint.setStrokeWidth(DensityUtil.dp2px(11));
        mBGPaint.setStyle(Paint.Style.STROKE);
        mBGPaint.setStrokeCap(Paint.Cap.ROUND);
        mBGPaint.setColor(Color.parseColor("#dddddd"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = 0;
        int heightSize = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            widthSize = MeasureSpec.getSize(widthMeasureSpec);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = DensityUtil.dp2px(275);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = MeasureSpec.getSize(heightMeasureSpec);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            heightSize = DensityUtil.dp2px(40);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(DensityUtil.dp2px(15), DensityUtil.dp2px(25), getWidth() - DensityUtil.dp2px(15), DensityUtil.dp2px(25), mBGPaint);
        canvas.drawLine(DensityUtil.dp2px(15), DensityUtil.dp2px(25), (getWidth() - DensityUtil.dp2px(30)) * mPercent + DensityUtil.dp2px(15), DensityUtil.dp2px(25), mProgressPaint);
    }
}
