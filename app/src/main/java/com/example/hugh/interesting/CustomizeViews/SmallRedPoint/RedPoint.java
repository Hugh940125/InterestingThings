package com.example.hugh.interesting.CustomizeViews.SmallRedPoint;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import com.example.hugh.interesting.Utils.DensityUtil;

/**
 * Created by Hugh on 2018/11/30.
 *
 */

public class RedPoint extends View {
    private Context mContext;
    private int mViewHeight;
    private int mViewWidth;
    private Paint mSmallCirclePaint;
    private Path mSmallCirclePath;
    private Path mBigCirclePath;
    private float mDownX;
    private float mDownY;
    private float mBigCircleCenterX;
    private float mSmallCircleCenterY;
    private float mSmallCircleCenterX;
    private float mBigCircleCenterY;
    private Paint mBigCirclePaint;
    private int mSmallRadius = 30;
    private int mBigRadius = 45;
    private float mP0X;
    private float mP0Y;
    private float mP1X;
    private float mP1Y;
    private float mP2X;
    private float mP2Y;
    private float mP3X;
    private float mP3Y;
    private Path mBezierPath;
    private float mControlPointX;
    private float mControlPointY;
    private Paint mBezierPaint;
    private Paint mTextPaint;
    private Region mBigCircleRegion;
    private boolean mSelected;
    private boolean mIsDrawBezier;
    private Rect mTextRect;
    private String mText;
    private Paint mBoundPaint;
    private Region mCircleClipRegion;
    private Region mLimitRegion;
    private Path mLimitPath;
    private Region mLimitClipRegion;
    private boolean is;
    private boolean mIsPointOut;
    private float mUpX;
    private float mUpY;

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
        mSmallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallCirclePaint.setColor(Color.RED);
        mBigCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBigCirclePaint.setColor(Color.RED);
        mBezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBezierPaint.setColor(Color.RED);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mSmallCirclePath = new Path();
        mBigCirclePath = new Path();
        mBigCirclePath = new Path();
        mBezierPath = new Path();
        mBigCircleRegion = new Region();
        mText = "5";
        mTextRect = new Rect();
        mTextPaint.setTextSize(DensityUtil.dp2px(15));
        mTextPaint.getTextBounds(mText,0,mText.length(),mTextRect);
        mBoundPaint = new Paint();
        mBoundPaint.setStyle(Paint.Style.STROKE);
        mBoundPaint.setColor(Color.RED);
        mBoundPaint.setStrokeWidth(DensityUtil.dp2px(1));
        mBoundPaint.setPathEffect(new DashPathEffect(new float[]{16, 4}, 0));//绘制长度为4的实线后再绘制长度为4的空白区域，0位间隔
        mLimitRegion = new Region();
        mLimitPath = new Path();
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = getHeight();
        mViewWidth = getWidth();
        mBigCircleCenterX = mViewWidth >> 1;
        mBigCircleCenterY = mViewHeight >> 1;
        mSmallCircleCenterX = mViewWidth >> 1;
        mSmallCircleCenterY = mViewHeight >> 1;
        mCircleClipRegion = new Region((int) (mBigCircleCenterX - mBigRadius), (int) (mBigCircleCenterY - mBigRadius), (int) (mBigCircleCenterX + mBigRadius), (int) (mBigCircleCenterY + mBigRadius));
        mLimitClipRegion = new Region((int)(mSmallCircleCenterX-300),(int)(mSmallCircleCenterX-300),(int)(mSmallCircleCenterX+300),(int)(mSmallCircleCenterY+300));
        mLimitPath.addCircle(mSmallCircleCenterX,mSmallCircleCenterY,300, Path.Direction.CW);
        mLimitRegion.setPath(mLimitPath,mLimitClipRegion);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBigCirclePath.reset();
        mSmallCirclePath.addCircle(mSmallCircleCenterX,mSmallCircleCenterY,mSmallRadius, Path.Direction.CW);
        canvas.drawPath(mSmallCirclePath, mSmallCirclePaint);
        mBigCirclePath.addCircle(mBigCircleCenterX,mBigCircleCenterY,mBigRadius, Path.Direction.CW);
        mBigCircleRegion.setPath(mBigCirclePath, mCircleClipRegion);
        canvas.drawPath(mBigCirclePath, mBigCirclePaint);
        mBezierPath.reset();
        if (mIsDrawBezier){
            mBezierPath.moveTo(mP0X,mP0Y);
            mBezierPath.quadTo(mControlPointX,mControlPointY,mP1X,mP1Y);
            mBezierPath.lineTo(mP2X, mP2Y);
            mBezierPath.quadTo(mControlPointX, mControlPointY, mP3X, mP3Y);
            mBezierPath.close();
        }
        canvas.drawPath(mBezierPath,mBezierPaint);
        canvas.drawText(mText,mBigCircleCenterX- (mTextRect.width() >> 1),mBigCircleCenterY + (mTextRect.height() >> 1), mTextPaint);
        canvas.drawPath(mLimitPath,mBoundPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                is = true;
                if (mBigCircleRegion.contains((int)mDownX,(int)mDownY)){
                    mSelected = true;
                }else {
                    mSelected = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float mMovedX = event.getX();
                float mMovedY = event.getY();
                mIsDrawBezier = true;
                if (mSelected){
                    float mDltX = mMovedX - mDownX;
                    float mDltY = mMovedY - mDownY;
                    mBigCircleCenterX = mMovedX;
                    mBigCircleCenterY = mMovedY;
                    double mTan = mDltY/mDltX;
                    double mATan = Math.atan(mTan);
                    mP0X = (float) (mSmallCircleCenterX+mSmallRadius*Math.sin(mATan));
                    mP0Y = (float) (mSmallCircleCenterY-mSmallRadius*Math.cos(mATan));
                    mP1X = (float) (mMovedX+mBigRadius*Math.sin(mATan));
                    mP1Y = (float) (mMovedY-mBigRadius*Math.cos(mATan));
                    mP2X = (float) (mMovedX-mBigRadius*Math.sin(mATan));
                    mP2Y = (float) (mMovedY+mBigRadius*Math.cos(mATan));
                    mP3X = (float) (mSmallCircleCenterX-mSmallRadius*Math.sin(mATan));
                    mP3Y = (float) (mSmallCircleCenterY+mSmallRadius*Math.cos(mATan));
                    mControlPointX = (mSmallCircleCenterX+mBigCircleCenterX)/2;
                    mControlPointY = (mSmallCircleCenterY+mBigCircleCenterY)/2;
                    if (!mLimitRegion.contains((int)mMovedX,(int)mMovedY)){
                        if (is){
                            is = false;
                            mIsPointOut = true;
                        }
                    }else {
                        mIsPointOut = false;
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                mUpX = mBigCircleCenterX;
                mUpY = mBigCircleCenterY;
//               if (mIsPointOut){
//                   mBigCircleCenterX = mViewWidth/2;
//                   mBigCircleCenterY = mViewHeight/2;
//                   mIsDrawBezier = false;
//                   mCircleClipRegion.set((int) (mBigCircleCenterX - mBigRadius), (int) (mBigCircleCenterY - mBigRadius), (int) (mBigCircleCenterX + mBigRadius), (int) (mBigCircleCenterY + mBigRadius));
//                   invalidate();
//               }else {
                   AnimatorSet animatorSet = new AnimatorSet();
                   OvershootInterpolator  overshootInterpolator = new OvershootInterpolator();
                   ValueAnimator mValueAnimatorX = ValueAnimator.ofFloat(mBigCircleCenterX,mSmallCircleCenterX);
                   mValueAnimatorX.setDuration(300);
                   mValueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                     @Override
                     public void onAnimationUpdate(ValueAnimator animation) {
                         Log.e("animator-X",animation.getAnimatedValue()+"");
                         mBigCircleCenterX = (float) animation.getAnimatedValue();
                     }
                   });
                   ValueAnimator mValueAnimatorY = ValueAnimator.ofFloat(mBigCircleCenterY,mSmallCircleCenterY);
                   mValueAnimatorY.setDuration(300);
                   mValueAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                       @Override
                       public void onAnimationUpdate(ValueAnimator animation) {
                           Log.e("animator-Y",animation.getAnimatedValue()+"");
                           mBigCircleCenterY = (float) animation.getAnimatedValue();
                           float mDltX = mBigCircleCenterX - mDownX;
                           float mDltY = mBigCircleCenterY - mDownY;
                           double mTan = mDltY/mDltX;
                           double mATan = Math.atan(mTan);
                           mP0X = (float) (mSmallCircleCenterX+mSmallRadius*Math.sin(mATan));
                           mP0Y = (float) (mSmallCircleCenterY-mSmallRadius*Math.cos(mATan));
                           mP1X = (float) (mBigCircleCenterX+mBigRadius*Math.sin(mATan));
                           mP1Y = (float) (mBigCircleCenterY-mBigRadius*Math.cos(mATan));
                           mP2X = (float) (mBigCircleCenterX-mBigRadius*Math.sin(mATan));
                           mP2Y = (float) (mBigCircleCenterY+mBigRadius*Math.cos(mATan));
                           mP3X = (float) (mSmallCircleCenterX-mSmallRadius*Math.sin(mATan));
                           mP3Y = (float) (mSmallCircleCenterY+mSmallRadius*Math.cos(mATan));
                           mControlPointX = (mSmallCircleCenterX+mBigCircleCenterX)/2;
                           mControlPointY = (mSmallCircleCenterY+mBigCircleCenterY)/2;
                           invalidate();
                       }
                   });
                   animatorSet.playTogether(mValueAnimatorX,mValueAnimatorY);
                   animatorSet.setInterpolator(overshootInterpolator);
                   animatorSet.start();
                //}
                performClick();
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
