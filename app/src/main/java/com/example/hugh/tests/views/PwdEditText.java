package com.example.hugh.tests.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.hugh.tests.utils.DensityUtil;

/**
 * Created by Hugh on 2018/11/28.
 *
 */

public class PwdEditText extends View {

    private Paint mFramePaint;
    private Paint mDividerPaint;
    private OnTextUpdateListener onTextUpdateListener;

    public PwdEditText(Context context) {
        super(context);
        init();
    }

    public PwdEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mFramePaint = new Paint();
        mFramePaint.setAntiAlias(true);
        mFramePaint.setColor(Color.BLACK);
        mFramePaint.setStyle(Paint.Style.STROKE);
        mFramePaint.setStrokeWidth(2);
        mDividerPaint = new Paint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setColor(Color.CYAN);
        mDividerPaint.setStyle(Paint.Style.STROKE);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = 0;
        int heightSize = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY){
            widthSize = MeasureSpec.getSize(widthMeasureSpec);
        }else if (widthMode == MeasureSpec.AT_MOST){
            widthSize = DensityUtil.dp2px(300);
        }
        if (heightMode == MeasureSpec.EXACTLY){
            heightSize = MeasureSpec.getSize(heightMeasureSpec);
        }else if (widthMode == MeasureSpec.AT_MOST){
            heightSize = DensityUtil.dp2px(50);
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFrame(canvas);
        drawDivider(canvas);
    }

    private void drawDivider(Canvas canvas) {
        float[] linesX = getLinesX();
        for (Float f:linesX){
            canvas.drawLine(f,0,f,getHeight(),mDividerPaint);
        }
    }

    private float[] getLinesX() {
        int width = getWidth()/6;
        float[] floats = new float[5];
        for (int i=0;i<5;i++){
            floats[i] = width*(i+1);
        }
        return floats;
    }

    private void drawFrame(Canvas canvas) {
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRect(rectF,mFramePaint);
    }

    interface OnTextUpdateListener{
        void OnTextUpdate();
    }

    public void addOnTextUpdateListener(OnTextUpdateListener onTextUpdateListener){
        this.onTextUpdateListener = onTextUpdateListener;
    }
}
