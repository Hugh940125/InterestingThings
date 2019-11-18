package com.example.hugh.interesting.CustomizeViews.CustomizeSeekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Hugh on 2019/11/7.
 */
public class CircleSeekBar extends View {
    private static final int MARK_WIDTH = 100;
    private static final float CURSOR_RADIUS = 50F;
    private static final float MAX_ANGLE = 300;
    private static final float SCALES = 15;
    private Paint paint;
    private Path bgPath;
    private int mWidth;
    private int mHeight;
    private Region bgRegion;
    private Path innerCirclePath;
    private Paint paint1;
    private Region innerCircleRegion;
    private float cursorX;
    private float cursorY;
    private float mSeekBarDegree;
    private Paint paint2;
    private boolean isDownInRegion = false;
    private Region cursorRegion;
    private double cycleArcTan;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private float pieceAngle;
    private Context mContext;

    public CircleSeekBar(Context context) {
        super(context);
        initView(context);
    }

    public CircleSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#FFDCDCDC"));
        paint.setStrokeWidth(MARK_WIDTH);

        paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        paint1.setAntiAlias(true);
        paint1.setColor(Color.RED);

        paint2 = new Paint();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);
        paint2.setStrokeWidth(5);
        paint2.setColor(Color.BLUE);

        bgPath = new Path();
        bgRegion = new Region();

        innerCirclePath = new Path();
        innerCircleRegion = new Region();

        cursorRegion = new Region();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        cursorX = mWidth - (MARK_WIDTH >> 1);
        cursorY = mHeight >> 1;
        startX = mWidth - MARK_WIDTH;
        startY = mHeight >> 1;
        endX = mWidth;
        endY = mHeight >> 1;
        cursorRegion.set((int) (cursorX - CURSOR_RADIUS), (int) (cursorY - CURSOR_RADIUS), (int) (cursorX + CURSOR_RADIUS), (int) (cursorY + CURSOR_RADIUS));
        bgPath.addCircle(mWidth >> 1, mHeight >> 1, (mWidth >> 1) - (MARK_WIDTH >> 1), Path.Direction.CW);
        bgRegion.setPath(bgPath, new Region(MARK_WIDTH >> 1, MARK_WIDTH >> 1, mHeight - (MARK_WIDTH >> 1), mWidth - (MARK_WIDTH >> 1)));
        //取出内部小圆的区域
        innerCirclePath.addCircle(mWidth >> 1, mHeight >> 1, (mWidth >> 1) - MARK_WIDTH, Path.Direction.CW);
        innerCircleRegion.setPath(innerCirclePath, new Region(MARK_WIDTH >> 1, MARK_WIDTH >> 1, mHeight - (MARK_WIDTH >> 1), mWidth - (MARK_WIDTH >> 1)));
        //取两个区域的补集，得到外圈的圆环
        bgRegion.op(innerCircleRegion, Region.Op.DIFFERENCE);
        pieceAngle = (MAX_ANGLE / SCALES);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(bgPath, paint);
        canvas.save();
        for (int i = 0; i < SCALES + 1; i++) {
            canvas.drawLine(mWidth - 20, mHeight >> 1, mWidth, mHeight >> 1, paint2);
            canvas.rotate(pieceAngle, mWidth >> 1, mHeight >> 1);
        }
        canvas.restore();
        //canvas.drawPath(innerCirclePath, paint1);
        canvas.drawCircle(cursorX, cursorY, CURSOR_RADIUS, paint1);
        canvas.drawArc((MARK_WIDTH >> 1), (MARK_WIDTH >> 1), mWidth - (MARK_WIDTH >> 1), mHeight - (MARK_WIDTH >> 1), 0, mSeekBarDegree, false, paint2);
        canvas.drawLine(startX, startY, endX, endY, paint2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                int downX = (int) event.getX();
                int downY = (int) event.getY();
                if (cursorRegion.contains(downX, downY)) {
                    isDownInRegion = true;
                } else {
                    isDownInRegion = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDownInRegion) {
                    int moveX = (int) event.getX();
                    int moveY = (int) event.getY();
                    float dltX = moveX - (mWidth >> 1);
                    float dltY = moveY - (mHeight >> 1);
                    double arcTan = Math.atan2(dltY, dltX);
                    cycleArcTan = arcTan < 0 ? arcTan + 2 * Math.PI : arcTan;
                    mSeekBarDegree = (float) Math.round(Math.toDegrees(cycleArcTan));
                    if (mSeekBarDegree >= 0 && mSeekBarDegree <= MAX_ANGLE) {
                        cursorX = (float) ((mWidth >> 1) + ((mWidth >> 1) - (MARK_WIDTH >> 1)) * Math.cos(arcTan));
                        cursorY = (float) ((mHeight >> 1) + ((mHeight >> 1) - (MARK_WIDTH >> 1)) * Math.sin(arcTan));
                        cursorRegion.set((int) (cursorX - CURSOR_RADIUS), (int) (cursorY - CURSOR_RADIUS), (int) (cursorX + CURSOR_RADIUS), (int) (cursorY + CURSOR_RADIUS));
                        startX = cursorX - (((int) (MARK_WIDTH * Math.cos(arcTan))) >> 1);
                        startY = cursorY - (((int) (MARK_WIDTH * Math.sin(arcTan))) >> 1);
                        endX = cursorX + (((int) (MARK_WIDTH * Math.cos(arcTan))) >> 1);
                        endY = cursorY + (((int) (MARK_WIDTH * Math.sin(arcTan))) >> 1);
                        invalidate();
                    } else {
                        isDownInRegion = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                judgePosition();
                break;
        }
        return true;
    }

    private void judgePosition() {
        for (int i = 0; i < SCALES + 1; i++) {
            if (mSeekBarDegree >= pieceAngle * i && mSeekBarDegree < pieceAngle * i + (pieceAngle / 2F)) {
                mSeekBarDegree = pieceAngle * i;
                Toast.makeText(mContext, "" + i, Toast.LENGTH_SHORT).show();
                break;
            } else if ((mSeekBarDegree >= pieceAngle * i + (pieceAngle / 2F) && mSeekBarDegree < pieceAngle * (i + 1))) {
                mSeekBarDegree = pieceAngle * (i + 1);
                Toast.makeText(mContext, "" + (i + 1), Toast.LENGTH_SHORT).show();
                break;
            }
        }
        double arcTan = Math.toRadians(mSeekBarDegree);
        arcTan = arcTan < 2 * Math.PI ? arcTan - 2 * Math.PI : arcTan;
        if (mSeekBarDegree >= 0 && mSeekBarDegree <= MAX_ANGLE) {
            cursorX = (float) ((mWidth >> 1) + ((mWidth >> 1) - (MARK_WIDTH >> 1)) * Math.cos(arcTan));
            cursorY = (float) ((mHeight >> 1) + ((mHeight >> 1) - (MARK_WIDTH >> 1)) * Math.sin(arcTan));
            cursorRegion.set((int) (cursorX - CURSOR_RADIUS), (int) (cursorY - CURSOR_RADIUS), (int) (cursorX + CURSOR_RADIUS), (int) (cursorY + CURSOR_RADIUS));
            startX = cursorX - (((int) (MARK_WIDTH * Math.cos(arcTan))) >> 1);
            startY = cursorY - (((int) (MARK_WIDTH * Math.sin(arcTan))) >> 1);
            endX = cursorX + (((int) (MARK_WIDTH * Math.cos(arcTan))) >> 1);
            endY = cursorY + (((int) (MARK_WIDTH * Math.sin(arcTan))) >> 1);
            invalidate();
        }
    }
}
