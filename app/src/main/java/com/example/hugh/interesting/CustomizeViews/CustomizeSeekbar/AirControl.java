package com.example.hugh.interesting.CustomizeViews.CustomizeSeekbar;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.hugh.interesting.Utils.DensityUtil;

/**
 * Created by Hugh on 2019/12/4.
 */
public class AirControl extends View {
    private static final int RING_WIDTH = DensityUtil.dp2px(30);
    private static final int SHADER_WIDTH = DensityUtil.dp2px(10);
    private static final float MAX_ANGLE = 270;
    private static final float GAPS = 16;
    private int mWidth;
    private int mHeight;
    private RectF mBigRingRectF;
    private Paint mBigRingPaint;
    private Paint mMarkPaint;
    private Paint mBgPaint;
    private BlurMaskFilter blurMaskFilter;
    private Paint mCenterCirclePaint;
    private Paint mTemperaturePaint;
    private Rect mTempTextRect;
    private String mTempText = "16";
    private String mTempUnit = "℃";
    private String mCurrentTempText = "当前室内温度：26℃";
    private String mStatusText = "开启";
    private String[] mTempTexts = new String[]{"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"};
    private Rect mTempUnitRect;
    private Rect mCurrentTempTextRect;
    private Rect mStatusRect;
    private Paint mTempUnitPaint;
    private Paint mStatusPaint;
    private float mPointerDegree;
    private float cursorX;
    private float cursorY;
    private Region mCursorRegion;
    private int mCursorRadius = 12;
    private boolean isDownInRegion;
    private RectF cursorArcRectF;
    private Paint mCursorArcPaint;
    private float mGapAngle;

    public AirControl(Context context) {
        this(context, null);
    }

    public AirControl(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AirControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mBigRingPaint = new Paint();
        mBigRingPaint.setStyle(Paint.Style.STROKE);
        mBigRingPaint.setAntiAlias(true);
        mBigRingPaint.setColor(Color.WHITE);
        mBigRingPaint.setStrokeWidth(RING_WIDTH);

        mMarkPaint = new Paint();
        mMarkPaint.setStyle(Paint.Style.STROKE);
        mMarkPaint.setAntiAlias(true);
        mMarkPaint.setColor(Color.parseColor("#FFE9EDF3"));
        mMarkPaint.setStrokeWidth(DensityUtil.dp2px(0.5F));

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(Color.parseColor("#FFF1F5F9"));
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, mBgPaint);

        mCenterCirclePaint = new Paint();
        mCenterCirclePaint.setStyle(Paint.Style.FILL);
        mCenterCirclePaint.setAntiAlias(true);
        mCenterCirclePaint.setColor(Color.parseColor("#FFFFFFFF"));
        setLayerType(LAYER_TYPE_SOFTWARE, mCenterCirclePaint);

        mTemperaturePaint = new Paint();
        mTemperaturePaint.setStyle(Paint.Style.FILL);
        mTemperaturePaint.setAntiAlias(true);
        mTemperaturePaint.setSubpixelText(true);
        mTemperaturePaint.setColor(Color.parseColor("#FF222222"));
        mTemperaturePaint.setTextSize(DensityUtil.sp2px(context, 40));
        mTemperaturePaint.setFakeBoldText(true);

        mTempUnitPaint = new Paint();
        mTempUnitPaint.setStyle(Paint.Style.FILL);
        mTempUnitPaint.setAntiAlias(true);
        mTempUnitPaint.setSubpixelText(true);
        mTempUnitPaint.setColor(Color.parseColor("#FF222222"));
        mTempUnitPaint.setTextSize(DensityUtil.sp2px(context, 18));

        mStatusPaint = new Paint();
        mStatusPaint.setStyle(Paint.Style.FILL);
        mStatusPaint.setAntiAlias(true);
        mStatusPaint.setSubpixelText(true);
        mStatusPaint.setColor(Color.parseColor("#FF707F93"));
        mStatusPaint.setTextSize(DensityUtil.sp2px(context, 12));

        mCursorArcPaint = new Paint();
        mCursorArcPaint.setStyle(Paint.Style.STROKE);
        mCursorArcPaint.setAntiAlias(true);
        mCursorArcPaint.setColor(Color.parseColor("#FF4690EF"));
        mCursorArcPaint.setStrokeWidth(DensityUtil.dp2px(5));

        mTempTextRect = new Rect();
        mTempUnitRect = new Rect();
        mCurrentTempTextRect = new Rect();
        mStatusRect = new Rect();

        mCursorRegion = new Region();
        mGapAngle = MAX_ANGLE / GAPS;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        mBigRingRectF = new RectF((RING_WIDTH >> 1) + SHADER_WIDTH, (RING_WIDTH >> 1) + SHADER_WIDTH, mWidth - (RING_WIDTH >> 1) - SHADER_WIDTH, mHeight - (RING_WIDTH >> 1) - SHADER_WIDTH);
        blurMaskFilter = new BlurMaskFilter(SHADER_WIDTH, BlurMaskFilter.Blur.SOLID);
        //游标的初始坐标
        cursorX = mWidth >> 1;
        cursorY = SHADER_WIDTH + RING_WIDTH + DensityUtil.dp2px(15);
        //游标的初始区域
        mCursorRegion.set((int) cursorX - 50, (int) cursorY - 50, (int) cursorX + 50, (int) cursorY + 50);
        //游标后面圆弧的区域
        cursorArcRectF = new RectF(SHADER_WIDTH + RING_WIDTH + DensityUtil.dp2px(12), SHADER_WIDTH + RING_WIDTH + DensityUtil.dp2px(12), mWidth - (SHADER_WIDTH + RING_WIDTH + DensityUtil.dp2px(12)), mHeight - (SHADER_WIDTH + RING_WIDTH + DensityUtil.dp2px(12)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //边缘阴影效果
        mBgPaint.setMaskFilter(blurMaskFilter);
        //带边缘阴影的背景
        canvas.drawCircle(mWidth >> 1, mHeight >> 1, (mWidth >> 1) - SHADER_WIDTH + DensityUtil.dp2px(2), mBgPaint);
        //表盘
        canvas.drawArc(mBigRingRectF, 270, 360, false, mBigRingPaint);
        //表盘刻度
        canvas.save();
        for (int i = 0; i < 360; i++) {
            canvas.drawLine(mWidth >> 1, (RING_WIDTH >> 2) + SHADER_WIDTH, mWidth >> 1, (RING_WIDTH >> 2) + (RING_WIDTH >> 1) + SHADER_WIDTH, mMarkPaint);
            canvas.rotate(2, mWidth >> 1, mHeight >> 1);
        }
        canvas.restore();
        //中间的小圆
        canvas.drawCircle(mWidth >> 1, mHeight >> 1, (mWidth >> 1) - SHADER_WIDTH - RING_WIDTH - DensityUtil.dp2px(15), mCenterCirclePaint);
        //获取各个字符串的尺寸
        mTemperaturePaint.getTextBounds(mTempText, 0, mTempText.length(), mTempTextRect);
        mTempUnitPaint.getTextBounds(mTempUnit, 0, mTempUnit.length(), mTempUnitRect);
        mStatusPaint.getTextBounds(mCurrentTempText, 0, mCurrentTempText.length(), mCurrentTempTextRect);
        mStatusPaint.getTextBounds(mStatusText, 0, mStatusText.length(), mStatusRect);
        //温度
        canvas.drawText(mTempText, (mWidth >> 1) - (mTempTextRect.width() >> 1) - (mTempUnitRect.width() >> 1), (mHeight >> 1), mTemperaturePaint);
        canvas.drawText(mTempUnit, (mWidth >> 1) + (mTempTextRect.width() >> 1) - (mTempUnitRect.width() >> 1) + DensityUtil.dp2px(3), (mHeight >> 1) - mTempUnitRect.height() - DensityUtil.dp2px(5), mTempUnitPaint);
        //当前室内温度
        mStatusPaint.setColor(Color.parseColor("#FF707F93"));
        canvas.drawText(mCurrentTempText, (mWidth >> 1) - (mCurrentTempTextRect.width() >> 1), (mHeight >> 1) + mCurrentTempTextRect.height() + DensityUtil.dp2px(10), mStatusPaint);
        //状态
        mStatusPaint.setColor(Color.parseColor("#FF4690EF"));
        canvas.drawText(mStatusText, (mWidth >> 1) - (mStatusRect.width() >> 1), (mHeight >> 1) + mCurrentTempTextRect.height() + DensityUtil.dp2px(20) + mStatusRect.height(), mStatusPaint);
        //游标屁股后的圆弧
        mStatusPaint.setColor(Color.parseColor("#FF4690EF"));
        canvas.drawArc(cursorArcRectF, 270, mPointerDegree, false, mCursorArcPaint);
        //游标
        canvas.drawCircle(cursorX, cursorY, DensityUtil.dp2px(mCursorRadius), mStatusPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                int downX = (int) event.getX();
                int downY = (int) event.getY();
                if (mCursorRegion.contains(downX, downY)) {
                    isDownInRegion = true;
                } else {
                    isDownInRegion = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                if (isDownInRegion) {
                    int moveX = (int) event.getX();
                    int moveY = (int) event.getY();
                    float dltX = moveX - (mWidth >> 1);
                    float dltY = moveY - (mHeight >> 1);
                    double arcTan = Math.atan2(dltY, dltX);
                    double cycleArcTan = arcTan < 0 ? arcTan + 2 * Math.PI : arcTan;
                    mPointerDegree = (float) Math.round(Math.toDegrees(cycleArcTan));
                    if (mPointerDegree >= 270) {
                        mPointerDegree = mPointerDegree - 270;
                    } else {
                        mPointerDegree = mPointerDegree + 90;
                    }
                    if (mPointerDegree >= 0 && mPointerDegree < MAX_ANGLE) {
                        //刷新游标的区域
                        cursorX = (float) ((mWidth >> 1) + ((mWidth >> 1) - SHADER_WIDTH - RING_WIDTH - DensityUtil.dp2px(15)) * Math.cos(arcTan));
                        cursorY = (float) ((mHeight >> 1) + ((mWidth >> 1) - SHADER_WIDTH - RING_WIDTH - DensityUtil.dp2px(15)) * Math.sin(arcTan));
                        mCursorRegion.set((int) cursorX - 50, (int) cursorY - 50, (int) cursorX + 50, (int) cursorY + 50);
                        int position = (int) (mPointerDegree / mGapAngle);
                        if (position >= 0 && position < mTempTexts.length) {
                            mTempText = mTempTexts[position];
                        }
                        invalidate();
                    } else {
                        isDownInRegion = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                if (isDownInRegion) {
                    judgePosition();
                    performClick();
                }
                break;
            default:
                if (isDownInRegion) {
                    judgePosition();
                    performClick();
                }
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void judgePosition() {
        float PointerDegree;
        int index = 0;
        for (int i = 0; i < GAPS + 1; i++) {
            if (mPointerDegree >= mGapAngle * i && mPointerDegree <= mGapAngle * i + (mGapAngle / 2F)) {
                mPointerDegree = mGapAngle * i;
                index = i;
                break;
            } else if ((mPointerDegree >= mGapAngle * i + (mGapAngle / 2F) && mPointerDegree <= mGapAngle * (i + 1))) {
                mPointerDegree = mGapAngle * (i + 1);
                index = i + 1;
                break;
            }
        }
        if (index >= 0 && index < mTempTexts.length) {
            mTempText = mTempTexts[index];
            Log.e("位置", index + "");
        }
        if (mPointerDegree >= 90) {
            PointerDegree = mPointerDegree - 90;
        } else {
            PointerDegree = mPointerDegree + 270;
        }
        double arcTan = Math.toRadians(PointerDegree);
        arcTan = arcTan < 2 * Math.PI ? arcTan - 2 * Math.PI : arcTan;
        if (mPointerDegree >= 0 && mPointerDegree <= MAX_ANGLE) {
            cursorX = (float) ((mWidth >> 1) + ((mWidth >> 1) - SHADER_WIDTH - RING_WIDTH - DensityUtil.dp2px(15)) * Math.cos(arcTan));
            cursorY = (float) ((mHeight >> 1) + ((mWidth >> 1) - SHADER_WIDTH - RING_WIDTH - DensityUtil.dp2px(15)) * Math.sin(arcTan));
            mCursorRegion.set((int) cursorX - 30, (int) cursorY - 30, (int) cursorX + 30, (int) cursorY + 30);
            invalidate();
        }
    }

    public void setData(int temp, String roomTemp, String status) {
        if (temp > 0) {
            int i = temp - 16;
            mPointerDegree = mGapAngle * i;
        }
        if (roomTemp != null) {
            mCurrentTempText = String.format("当前室内温度：%s℃", roomTemp);
        }
        if (status != null) {
            mStatusText = status;
        }
        judgePosition();
    }
}
