package com.example.hugh.interesting.Chart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import com.example.hugh.interesting.Utils.DensityUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by Hugh on 2018/12/7.
 *
 */

public class LineChart extends View {

    private Paint mAxisPaint;
    private int mXMax = 100;
    private int mYMax = 100;
    private int mXCells = 10;
    private int mYCells = 10;
    private Bitmap mBufferBitmap;
    private Canvas mBufferCanvas;
    private float mLastX = 0;
    private float mLastY = 0;
    private int mHeight;
    private int mWidth;
    private long index = 0;
    private int mXStep;
    private int mYStep;
    private Paint mLinePaint;
    private Path mLinePath;
    private float length;
    private Handler mHandler;

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

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.STROKE);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(5F);
        mLinePaint.setPathEffect(cornerPathEffect);

        mLinePath = new Path();

        mHandler = new LineHandler();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (mBufferCanvas != null){
                    if (index==0){
                        mLinePath.moveTo(mLastX,mLastY);
                    }else {
                        float stopY = (float) (Math.random() * 100) * mYStep;
                        mLinePath.lineTo(index* mXStep,stopY);
                        mBufferCanvas.drawPath(mLinePath,mLinePaint);
                        mLinePath.reset();
                        mLinePath.moveTo(index* mXStep,stopY);
                        PathMeasure measure = new PathMeasure(mLinePath, false);
                        length = measure.getLength();
                    }
                    index++;
                    Message message = mHandler.obtainMessage();
                    message.what = 1;
                    mHandler.sendMessage(message);
                    //invalidate();
                }
            }
        };
        timer.schedule(timerTask,1000,1000);
        timerTask.run();
    }

    private class LineHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                ValueAnimator valueAnimator = ObjectAnimator.ofFloat(1.0f, 0.0f);
                valueAnimator.setDuration(3000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        setPhase((Float) animation.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        }
    }

    public void setPhase(float phase) {
        mLinePaint.setPathEffect(createPathEffect(length, phase, 0.0f));
        invalidate();
    }

    private static PathEffect createPathEffect(float pathLength, float phase, float offset) {
        return new DashPathEffect(new float[] { pathLength, pathLength },
                Math.max(phase * pathLength, offset));
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
        mHeight = getHeight();
        mWidth = getWidth();
        canvas.drawLine(DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(16),getRight()-DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(16),mAxisPaint);//横坐标
        canvas.drawLine(DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(16),DensityUtil.dp2px(16),getTop()+DensityUtil.dp2px(16),mAxisPaint);//纵坐标
        int mXCell = (mWidth-DensityUtil.dp2px(40)) / mXCells;
        int mYCell = (mHeight-DensityUtil.dp2px(40)) / mYCells;
        for (int i=0;i<=mXCells;i++){
            canvas.drawLine(mXCell*i+DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(16),mXCell*i+DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(20),mAxisPaint);
        }
        for (int i=0;i<=mYCells;i++){
            canvas.drawLine(DensityUtil.dp2px(16),getBottom()-DensityUtil.dp2px(16)-mYCell*i,DensityUtil.dp2px(20),getBottom()-DensityUtil.dp2px(16)-mYCell*i,mAxisPaint);
        }
    }

    private void drawLine(Canvas canvas){
        mXStep = (mWidth- DensityUtil.dp2px(40)) / mXMax;
        mYStep = (mHeight- DensityUtil.dp2px(40)) / mYMax;
        if (mBufferBitmap == null) {
            mBufferBitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            mBufferCanvas = new Canvas(mBufferBitmap);
        }
        canvas.drawBitmap(mBufferBitmap,0,0,mAxisPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAxis(canvas);
        drawLine(canvas);
    }
}
