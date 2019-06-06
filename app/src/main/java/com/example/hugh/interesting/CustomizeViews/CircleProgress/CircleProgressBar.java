package com.example.hugh.interesting.CustomizeViews.CircleProgress;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.hugh.interesting.Utils.DensityUtil;

/**
 * Created by Hugh on 2019/6/3.
 */
public class CircleProgressBar extends View {

    private Paint mProgressPaint;
    private int mCenterX;
    private int mCenterY;
    private Paint mNumberPaint;
    private Paint mTextPaint;
    private int mPercent = 98;
    private int mAngle = (int) (mPercent / 100F * 360);
    private Rect mDescRect;
    private Rect mUnitRect;
    private Rect mNumberRect;
    private RectF rectF;
    private int mTextPadding = DensityUtil.dp2px(2);
    private int mCurrentAngle = 0;
    private int mCurrentNumber = 0;
    private final String mDesc;
    private final String mUnit;

    {
        mDesc = "已完成率";
        mUnit = "%";
    }

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(Color.parseColor("#3092ea"));
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(DensityUtil.dp2px(10));
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        mNumberPaint = new Paint();
        mNumberPaint.setAntiAlias(true);
        mNumberPaint.setColor(Color.parseColor("#3092ea"));
        mNumberPaint.setTextSize(DensityUtil.sp2px(context, 60));
        mNumberPaint.setFakeBoldText(true);
        mNumberPaint.setStyle(Paint.Style.FILL);
        mNumberPaint.setSubpixelText(true);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.parseColor("#3092ea"));
        mTextPaint.setTextSize(DensityUtil.sp2px(context, 18));
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setSubpixelText(true);

        mDescRect = new Rect();
        mUnitRect = new Rect();
        mNumberRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(DensityUtil.dp2px(10), DensityUtil.dp2px(10), getWidth() - DensityUtil.dp2px(10), getHeight() - DensityUtil.dp2px(10));
        mCenterX = getWidth() >> 1;
        mCenterY = getHeight() >> 1;
    }

    public void setData(int percent) {
        this.mPercent = percent;
    }

    public void start() {
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator AngleValueAnimator = ValueAnimator.ofInt(0, mAngle);
        AngleValueAnimator.addUpdateListener(animation -> {
            mCurrentAngle = (int) animation.getAnimatedValue();
            invalidate();
        });
        AngleValueAnimator.setDuration(1500);
//        AngleValueAnimator.setInterpolator(new OvershootInterpolator());
        AngleValueAnimator.setInterpolator(new DecelerateInterpolator());

        ValueAnimator NumberValueAnimator = ValueAnimator.ofInt(0, mPercent);
        NumberValueAnimator.addUpdateListener(animation -> mCurrentNumber = (int) animation.getAnimatedValue());
        NumberValueAnimator.setDuration(1500);
//        NumberValueAnimator.setInterpolator(new OvershootInterpolator());
        NumberValueAnimator.setInterpolator(new DecelerateInterpolator());

        animatorSet.playTogether(AngleValueAnimator, NumberValueAnimator);
        animatorSet.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String percent = String.valueOf(mCurrentNumber);
        mTextPaint.getTextBounds(mDesc, 0, mDesc.length(), mDescRect);
        mTextPaint.getTextBounds(mUnit, 0, mUnit.length(), mUnitRect);
        mNumberPaint.getTextBounds(percent, 0, percent.length(), mNumberRect);
        canvas.drawArc(rectF, 225, mCurrentAngle, false, mProgressPaint);
        canvas.drawText(percent, mCenterX - (mNumberRect.width() >> 1) - (mUnitRect.width() >> 1), mCenterY + (mNumberRect.height() >> 1) - (mDescRect.height() >> 1) - mTextPadding, mNumberPaint);
        canvas.drawText(mUnit, mCenterX + (mNumberRect.width() >> 1), mCenterY + (mNumberRect.height() >> 1) - (mUnitRect.height() >> 1) - mTextPadding, mTextPaint);
        canvas.drawText(mDesc, mCenterX - (mDescRect.width() >> 1), mCenterY + (mNumberRect.height() >> 1) + (mDescRect.height() >> 1) + mTextPadding, mTextPaint);
    }
}
