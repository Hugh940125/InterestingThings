package com.example.hugh.interesting.CustomizeViews.ProgressBar;

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

/**
 * Created by Hugh on 2019/7/8.
 */
public class VoteBar extends View {

    private int mHeight;
    private int mWidth;
    private Paint mLeftPaint;
    private Paint mRightPaint;
    private Paint mLinePaint;
    private Paint mTextPaint;
    private Bitmap leftProgressBitmap;
    private Bitmap rightProgressBitmap;
    private Rect leftRect;
    private Rect rightRect;
    private Rect leftDescRect;
    private Rect rightDescRect;
    private String leftDesc = "正方";
    private String rightDesc = "反方";
    private int leftVotes = 5;
    private int rightVotes = 12;
    private Rect leftVotesRect;
    private Rect rightVotesRect;
    private double leftPercent;
    private int downX;
    private int downY;
    private Boolean isVoted = false;

    public VoteBar(Context context) {
        this(context, null);
    }

    public VoteBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mLeftPaint = new Paint();
        mLeftPaint.setAntiAlias(true);

        mRightPaint = new Paint();
        mRightPaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(DensityUtil.dp2px(4));

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setSubpixelText(true);
        mTextPaint.setTextSize(DensityUtil.sp2px(context, 10));

        Drawable leftProgress = getResources().getDrawable(R.drawable.left_rounded_rect, null);
        leftProgressBitmap = drawableToBitmap(leftProgress);
        Drawable rightProgress = getResources().getDrawable(R.drawable.right_rounded_rect, null);
        rightProgressBitmap = drawableToBitmap(rightProgress);

        leftDescRect = new Rect();
        rightDescRect = new Rect();
        leftVotesRect = new Rect();
        rightVotesRect = new Rect();

        leftRect = new Rect();
        rightRect = new Rect();
    }

    public void setData(String leftDesc, int leftVotes, String rightDesc, int rightVotes, double leftPercent) {
        this.leftDesc = leftDesc;
        this.leftVotes = leftVotes;
        this.rightDesc = rightDesc;
        this.rightVotes = rightVotes;
        this.leftPercent = leftPercent;
        invalidate();
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
            widthSize = DensityUtil.dp2px(300);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = MeasureSpec.getSize(heightMeasureSpec);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            heightSize = DensityUtil.dp2px(50);
        }
        setMeasuredDimension(widthSize, heightSize);
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getHeight();
        mWidth = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        leftPercent = leftVotes * 1.0F / ((leftVotes + rightVotes) * 1.0F);
        int divider = (int) ((mWidth - DensityUtil.dp2px(10)) * leftPercent + DensityUtil.dp2px(5));
        String leftVotesString = String.valueOf(leftVotes);
        String rightVotesString = String.valueOf(rightVotes);
        leftRect.set(DensityUtil.dp2px(5), DensityUtil.dp2px(5), divider, mHeight - DensityUtil.dp2px(5));
        rightRect.set(divider, DensityUtil.dp2px(5), mWidth - DensityUtil.dp2px(5), mHeight - DensityUtil.dp2px(5));
        mTextPaint.getTextBounds(leftDesc, 0, leftDesc.length(), leftDescRect);
        mTextPaint.getTextBounds(leftVotesString, 0, leftVotesString.length(), leftVotesRect);
        mTextPaint.getTextBounds(rightDesc, 0, rightDesc.length(), rightDescRect);
        mTextPaint.getTextBounds(rightVotesString, 0, rightVotesString.length(), rightVotesRect);
        canvas.drawBitmap(leftProgressBitmap, null, leftRect, mLeftPaint);
        canvas.drawBitmap(rightProgressBitmap, null, rightRect, mRightPaint);
        canvas.drawLine(divider + DensityUtil.dp2px(2), DensityUtil.dp2px(3), divider - DensityUtil.dp2px(2), mHeight - DensityUtil.dp2px(3), mLinePaint);
        canvas.drawText(leftDesc, DensityUtil.dp2px(30) - (leftDescRect.width() >> 1), mHeight >> 1, mTextPaint);
        canvas.drawText(rightDesc, mWidth - DensityUtil.dp2px(30) - (rightDescRect.width() >> 1), mHeight >> 1, mTextPaint);
        canvas.drawText(leftVotesString, DensityUtil.dp2px(30) - (leftVotesRect.width() >> 1), (mHeight >> 1) + 30, mTextPaint);
        canvas.drawText(rightVotesString, mWidth - DensityUtil.dp2px(30) - (rightVotesRect.width() >> 1), (mHeight >> 1) + 30, mTextPaint);
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
                double distance = Math.sqrt((upY - downY) ^ 2 + (upX - downX) ^ 2);
                if (distance < 20) {
                    if (leftRect.contains(downX, downY)) {
                        leftVotes++;
                        invalidate();
                        isVoted = true;
                    } else if (rightRect.contains(downX, downY)) {
                        rightVotes++;
                        invalidate();
                        isVoted = true;
                    }
                }
                break;
        }
        return true;
    }
}
