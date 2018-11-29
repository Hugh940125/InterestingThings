package com.example.hugh.tests.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.hugh.tests.utils.DensityUtil;

import java.util.ArrayList;

/**
 * Created by Hugh on 2018/11/28.
 *
 */

public class PwdEditText extends View {

    private Paint mFramePaint;
    private Paint mDividerPaint;
    ArrayList<String> buffer = new ArrayList();
    private OnPwdCompleteListener onPwdCompleteListener;
    private Context mContext;

    public PwdEditText(Context context) {
        super(context);
        init(context);
    }

    public PwdEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mFramePaint = new Paint();
        mFramePaint.setAntiAlias(true);
        mFramePaint.setColor(Color.BLACK);
        mFramePaint.setStyle(Paint.Style.STROKE);
        mFramePaint.setStrokeWidth(2);
        mDividerPaint = new Paint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setColor(Color.CYAN);
        mDividerPaint.setStyle(Paint.Style.STROKE);
        mContext = context;
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
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        mFramePaint.setTextSize(50);
        mFramePaint.setTextAlign(Paint.Align.CENTER);
        float[] linesX = getLinesX();
        if (buffer.size() > 0){
            for (int i=0;i<buffer.size();i++){
                String str = buffer.get(i);
                Rect rect = new Rect();
                mFramePaint.getTextBounds(str,0,str.length(),rect);
                if (i<=4){
                    canvas.drawText(str,linesX[i]-linesX[0]/2,getHeight()/2+rect.height()/2,mFramePaint);
                }else if (i==5){
                    canvas.drawText(str,linesX[4]+linesX[0]/2,getHeight()/2+rect.height()/2,mFramePaint);
                }
            }
            if (buffer.size() == 6){
                //onPwdCompleteListener.onPwdComplete();
                executionAnimation();
                Vibrator vibrator = (Vibrator)mContext.getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null) {
                    vibrator.vibrate(200);
                }
                buffer.clear();
            }
        }
    }

    private void executionAnimation(){
        ObjectAnimator translationX = ObjectAnimator.ofFloat(this, "translationX", 0.0F, 10.0F, -10.0F, 0.0F);
        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
        translationX.setInterpolator(accelerateDecelerateInterpolator);
        translationX.setDuration(500);
        translationX.start();
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

    public void setText(String text){
        buffer.add(text);
        if (buffer.size() <= 6){

        }else {

        }
        invalidate();
    }

    interface OnPwdCompleteListener{
        void onPwdComplete();
    }

    public void setOnPwdCompleteListener(OnPwdCompleteListener onPwdCompleteListener){
        this.onPwdCompleteListener = onPwdCompleteListener;
    }
}
