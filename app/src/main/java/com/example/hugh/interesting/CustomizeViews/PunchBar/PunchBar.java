package com.example.hugh.interesting.CustomizeViews.PunchBar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.hugh.interesting.R;
import com.example.hugh.interesting.Utils.DensityUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Hugh on 2019/7/11.
 */
public class PunchBar extends View {
    private List<EachDayInfo> mDayInfos = new ArrayList<>(Arrays.asList(new EachDayInfo("7.11", true, false), new EachDayInfo("7.12", true, false), new EachDayInfo("今日", false, true), new EachDayInfo("7.14", false, false), new EachDayInfo("7.15", false, false), new EachDayInfo("7.16", false, false), new EachDayInfo("7.17", false, false)));
    private int mPadding = DensityUtil.dp2px(5);
    private int mPunchSize = DensityUtil.dp2px(20);
    private int mWidth;
    private int mHeight;
    private Point[] mPunchCenters;
    private Rect[] mRects;
    private Point[] mTextCenters;
    private Paint mBgPaint;
    private Bitmap integralBitmap;
    private Paint mPunchAllRightPaint;
    private Paint mTextPaint;
    private Rect mTextRect;
    private Paint mRingPaint;
    private Bitmap punchedBitmap;
    private boolean isPunched = true;
    private int downX;
    private int downY;
    private Rect mTodayRect;
    private EachDayInfo mTodayInfo;

    public PunchBar(Context context) {
        this(context, null);
    }

    public PunchBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(Color.parseColor("#FFE7E8E9"));
        mBgPaint.setStrokeWidth(DensityUtil.dp2px(3));
        //Bitmap的画笔
        mPunchAllRightPaint = new Paint();
        mPunchAllRightPaint.setAntiAlias(true);
        //日期文字的画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setSubpixelText(true);
        mTextPaint.setColor(Color.parseColor("#FF1F2633"));
        mTextPaint.setTextSize(DensityUtil.sp2px(context, 12));
        //今日的环画笔
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(Color.parseColor("#FF5076ED"));
        //初始化Bitmap
        Drawable integralProgress = getResources().getDrawable(R.drawable.integral, null);
        integralBitmap = drawableToBitmap(integralProgress);
        Drawable punchedProgress = getResources().getDrawable(R.drawable.punched, null);
        punchedBitmap = drawableToBitmap(punchedProgress);
        //用于测量文字的Rect
        mTextRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        float section = (mWidth - (mPadding << 1) - mPunchSize) * 1.0F / (mDayInfos.size() - 1);
        getPunchCentersAndRectsAndTextCenters(section);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void getPunchCentersAndRectsAndTextCenters(float section) {
        mPunchCenters = new Point[mDayInfos.size()];
        mTextCenters = new Point[mDayInfos.size()];
        mRects = new Rect[mDayInfos.size()];
        for (int i = 0; i < mDayInfos.size(); i++) {
            //打卡图案的中心点
            int punchCenterX = (int) (mPadding + (mPunchSize >> 1) + section * i);
            int punchCenterY = (mHeight >> 1) + (mPunchSize >> 1) + DensityUtil.dp2px(4);
            Point punchCenterPoint = new Point(punchCenterX, punchCenterY);
            mPunchCenters[i] = punchCenterPoint;
            //画打卡图案的Rect
            mRects[i] = new Rect(punchCenterX - (mPunchSize >> 1), punchCenterY - (mPunchSize >> 1), punchCenterX + (mPunchSize >> 1), punchCenterY + (mPunchSize >> 1));
            //文字的中心点
            String date = mDayInfos.get(i).getDate();
            mTextPaint.getTextBounds(date, 0, date.length(), mTextRect);
            int textCenterX = punchCenterX - (mTextRect.width() >> 1);
            int textCenterY = (mHeight >> 1) - (mTextRect.height() >> 1);
            Point textCenterPoint = new Point(textCenterX, textCenterY);
            mTextCenters[i] = textCenterPoint;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mPunchCenters.length; i++) {
            if (i > 0) {
                //背景的灰色横线
                canvas.drawLine(mPunchCenters[i - 1].x, mPunchCenters[i - 1].y, mPunchCenters[i].x, mPunchCenters[i].y, mBgPaint);
            }
            //背景的灰色圆
            canvas.drawCircle(mPunchCenters[i].x, mPunchCenters[i].y, DensityUtil.dp2px(6), mBgPaint);
            //区分是否打卡的日期，不同颜色画出
            EachDayInfo eachDayInfo = mDayInfos.get(i);
            if (eachDayInfo.getPunched() || eachDayInfo.getToaday()) {
                mTextPaint.setColor(Color.parseColor("#FF1F2633"));
            } else {
                mTextPaint.setColor(Color.parseColor("#FFA4A7AC"));
            }
            canvas.drawText(eachDayInfo.getDate(), mTextCenters[i].x, mTextCenters[i].y, mTextPaint);
        }
        for (int i = 0; i < mPunchCenters.length; i++) {
            EachDayInfo eachDayInfo = mDayInfos.get(i);
            //画今日的圆环
            if (eachDayInfo.getToaday()) {
                canvas.drawCircle(mPunchCenters[i].x, mPunchCenters[i].y, DensityUtil.dp2px(6), mRingPaint);
                mRingPaint.setColor(Color.parseColor("#FFFFFFFF"));
                canvas.drawCircle(mPunchCenters[i].x, mPunchCenters[i].y, DensityUtil.dp2px(4), mRingPaint);
                mTodayRect = mRects[i];
                mTodayInfo = eachDayInfo;
            }
            //最后的JB
            if (i == mPunchCenters.length - 1) {
                canvas.drawBitmap(integralBitmap, null, mRects[i], mPunchAllRightPaint);
            }
            //画已打卡的图案
            if (eachDayInfo.getPunched()) {
                canvas.drawBitmap(punchedBitmap, null, mRects[i], mPunchAllRightPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (mTodayRect.contains(downX,downY)&&mTodayRect.contains(upX,upY)) {
                    if (!mTodayInfo.getPunched()){
                        mTodayInfo.setPunched(true);
                        invalidate();
                        isPunched = false;
                    }
                }
                break;
        }
        return isPunched;
    }

    static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
