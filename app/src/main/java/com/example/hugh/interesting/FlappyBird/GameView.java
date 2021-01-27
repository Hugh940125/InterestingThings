package com.example.hugh.interesting.FlappyBird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static java.lang.Thread.sleep;

/**
 * Created by Hugh on 2019/5/28.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;
    private Path mPath;
    private float centerX;
    private float centerY;
    private boolean isDrawing;
    private boolean isFirst = true;
    private Paint mPaint;
    private long upTime;

    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        centerX = getWidth() >> 1;
        centerY = getHeight() >> 1;
        mPath.addCircle(centerX, centerY, 30, Path.Direction.CW);
        canvas.drawPath(mPath, mPaint);
        mHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Canvas canvas = mHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                mPath.reset();
                updatePosition(0, canvas);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (isFirst) {
                    new Thread(this).start();
                    isFirst = false;
                }
                upTime = System.currentTimeMillis();
                performClick();
                break;
        }
        return true;
    }

    private synchronized void updatePosition(int type, Canvas canvas) {
        if (type == 0) {
            centerY -= 100;
        } else if (type == 1) {
            centerY += 100;
        }
        mPath.addCircle(centerX, centerY, 30, Path.Direction.CW);
        canvas.drawPath(mPath, mPaint);
        mHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void run() {
        while (isDrawing) {
            if (System.currentTimeMillis() - upTime >= 1000) {
                Canvas canvas1 = mHolder.lockCanvas();
                canvas1.drawColor(Color.WHITE);
                mPath.reset();
                updatePosition(1, canvas1);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
