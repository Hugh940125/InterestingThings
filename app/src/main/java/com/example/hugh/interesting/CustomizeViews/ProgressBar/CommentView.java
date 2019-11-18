package com.example.hugh.interesting.CustomizeViews.ProgressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.hugh.interesting.R;
import com.example.hugh.interesting.Utils.DensityUtil;

/**
 * Created by Hugh on 2019/10/8.
 */
public class CommentView extends View {

    private Bitmap mGrayStar;
    private Bitmap mRedStar;
    private Paint starPaint;
    private Rect rect1;
    private Rect rect2;
    private Rect rect3;
    private Rect rect4;
    private Rect rect5;
    private int downX;
    private int downY;
    private int upX;
    private int upY;
    private int redStarCount;
    private boolean isDisplayMode;

    public int getRateStars() {
        return redStarCount;
    }

    public CommentView(Context context) {
        super(context);
        init(context, null);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommentView);
        isDisplayMode = typedArray.getBoolean(R.styleable.CommentView_isDisplayMode, false);
        typedArray.recycle();

        Drawable star_gray = ContextCompat.getDrawable(context, R.drawable.star_gray);
        mGrayStar = drawableToBitmap(star_gray);
        Drawable star_red = ContextCompat.getDrawable(context, R.drawable.star_red);
        mRedStar = drawableToBitmap(star_red);

        starPaint = new Paint();
        starPaint.setAntiAlias(true);
    }

    public void setStars(int stars) {
        redStarCount = stars;
        invalidate();
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
            widthSize = DensityUtil.dp2px(200);
        } else {
            widthSize = DensityUtil.dp2px(200);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = MeasureSpec.getSize(heightMeasureSpec);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            heightSize = DensityUtil.dp2px(25);
        } else {
            heightSize = DensityUtil.dp2px(25);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int section = getWidth() / 6;
        int height = getHeight();
        int interval = (getWidth() - 5 * height) / 4;
        if (isDisplayMode) {
            rect1 = new Rect(0, 0, height, height);
            rect2 = new Rect(interval + height, 0, interval + height * 2, height);
            rect3 = new Rect(interval * 2 + height * 2, 0, interval * 2 + height * 3, height);
            rect4 = new Rect(interval * 3 + height*3, 0, interval * 3 + height*4, height);
            rect5 = new Rect(getWidth() - height, 0, getWidth(), height);
        } else {
            rect1 = new Rect(section - (height >> 1), 0, section + (height >> 1), height);
            rect2 = new Rect(section * 2 - (height >> 1), 0, section * 2 + (height >> 1), height);
            rect3 = new Rect(section * 3 - (height >> 1), 0, section * 3 + (height >> 1), height);
            rect4 = new Rect(section * 4 - (height >> 1), 0, section * 4 + (height >> 1), height);
            rect5 = new Rect(section * 5 - (height >> 1), 0, section * 5 + (height >> 1), height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mGrayStar, null, rect1, starPaint);
        canvas.drawBitmap(mGrayStar, null, rect2, starPaint);
        canvas.drawBitmap(mGrayStar, null, rect3, starPaint);
        canvas.drawBitmap(mGrayStar, null, rect4, starPaint);
        canvas.drawBitmap(mGrayStar, null, rect5, starPaint);
        switch (redStarCount) {
            case 5:
                canvas.drawBitmap(mRedStar, null, rect5, starPaint);
            case 4:
                canvas.drawBitmap(mRedStar, null, rect4, starPaint);
            case 3:
                canvas.drawBitmap(mRedStar, null, rect3, starPaint);
            case 2:
                canvas.drawBitmap(mRedStar, null, rect2, starPaint);
            case 1:
                canvas.drawBitmap(mRedStar, null, rect1, starPaint);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isDisplayMode) {
            int actionMasked = event.getActionMasked();
            switch (actionMasked) {
                case MotionEvent.ACTION_DOWN:
                    downX = (int) event.getX();
                    downY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    upX = (int) event.getX();
                    upY = (int) event.getY();
                    if (Math.abs(downX - upX) < 10 && Math.abs(downY - upY) < 10) {
                        if (rect1.contains(upX, upY)) {
                            redStarCount = 1;
                        } else if (rect2.contains(upX, upY)) {
                            redStarCount = 2;
                        } else if (rect3.contains(upX, upY)) {
                            redStarCount = 3;
                        } else if (rect4.contains(upX, upY)) {
                            redStarCount = 4;
                        } else if (rect5.contains(upX, upY)) {
                            redStarCount = 5;
                        }
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
