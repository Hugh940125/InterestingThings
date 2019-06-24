package com.example.hugh.interesting.CustomizeViews.CustomizeSeekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.hugh.interesting.R;
import com.example.hugh.interesting.Utils.DensityUtil;

/**
 * Created by Hugh on 2019/3/11.
 *
 */

public class CustomizeSeekBar extends View {
    private int mViewHeight;
    private int mViewWidth;
    private int mViewLeft;
    private Paint mRulePaint;
    private Paint mCursorPaint;
    private float mCursorX;
    private float mCursorY;
    final float mCursorHeight = DensityUtil.dp2px(16);
    final float mRuleWidth = DensityUtil.dp2px(14);
    private Region mPointerRegion;
    private boolean mSelected;
    private Path mRegionPath;
    private int mInterval;
    private GearListener mGearListener;
    private Context mContext;
    private int halfWidth;
    private int halfHeight;
    private RectF mRuleRect;
    private RectF mCursorRect;

    public CustomizeSeekBar(Context context) {
        this(context,null);
    }

    public CustomizeSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mRulePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRulePaint.setColor(Color.RED);
        mRulePaint.setStrokeWidth(5);
        mCursorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCursorPaint.setColor(Color.RED);
    }

    public CustomizeSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void judgeGear(float mUpY){
        if (mUpY<mInterval/2){
            output(0);
            invalidateGear(mCursorHeight/2);
        }else if (mUpY>mInterval/2 && mUpY<=mInterval){
            output(1);
            invalidateGear(mCursorHeight/2 +mInterval);
        }else if (mUpY>mInterval && mUpY<=mInterval*3/2){
            output(1);
            invalidateGear(mCursorHeight/2 +mInterval);
        }else if (mUpY>mInterval*3/2 && mUpY<=mInterval*2){
            output(2);
            invalidateGear(mCursorHeight/2 +mInterval*2);
        }else if (mUpY>mInterval*2 && mUpY<=mInterval*5/2){
            output(2);
            invalidateGear(mCursorHeight/2 +mInterval*2);
        }else if (mUpY>mInterval*5/2 && mUpY<=mInterval*3){
            output(3);
            invalidateGear(mCursorHeight/2 +mInterval*3);
        }else if (mUpY>mInterval*3 && mUpY<=mInterval*7/2){
            output(3);
            invalidateGear(mCursorHeight/2 +mInterval*3);
        }else if (mUpY>mInterval*7/2 && mUpY<=mInterval*4){
            output(4);
            invalidateGear(mCursorHeight/2 +mInterval*4);
        }else if (mUpY>mInterval*4 && mUpY<=mInterval*9/2){
            output(4);
            invalidateGear(mCursorHeight/2 +mInterval*4);
        }else if (mUpY>mInterval*9/2 && mUpY<=mInterval*5){
            output(5);
            invalidateGear(mCursorHeight/2 +mInterval*5);
        }else if (mUpY>mInterval*5 && mUpY<=mInterval*11/2){
            output(5);
            invalidateGear(mCursorHeight/2 +mInterval*5);
        }else if (mUpY>mInterval*11/2){
            output(6);
            invalidateGear(mViewHeight-mCursorHeight/2);
        }
    }

    private void invalidateGear(float y){
        mCursorY = y;
        mRegionPath.addRect(mCursorX-halfWidth-30,mCursorY-halfHeight*3,mCursorX+halfWidth+30,mCursorY+halfHeight*3, Path.Direction.CW);
        mPointerRegion.setPath(mRegionPath,new Region((int)(mCursorX - halfWidth),(int)(mCursorY - halfHeight),(int)(mCursorX+halfWidth),(int)(mCursorY+halfHeight)));
        mCursorRect = new RectF(mCursorX - halfWidth, mCursorY - halfHeight, mCursorX + halfWidth, mCursorY + halfHeight);
        invalidate();
    }

    private void output(int position) {
        Toast.makeText(mContext, position+"", Toast.LENGTH_SHORT).show();
        if (mGearListener != null) {
            mGearListener.onGearSelected(position);
        }
    }

    public void setPosition(int position){
        if (mRegionPath != null && mRegionPath != null){
            switch (position){
                case 0:
                    output(0);
                    invalidateGear(mCursorHeight/2);
                    break;
                case 1:
                    output(1);
                    invalidateGear(mCursorHeight/2 +mInterval);
                    break;
                case 2:
                    output(2);
                    invalidateGear(mCursorHeight/2 +mInterval*2);
                    break;
                case 3:
                    output(3);
                    invalidateGear(mCursorHeight/2 +mInterval*3);
                    break;
                case 4:
                    output(4);
                    invalidateGear(mCursorHeight/2 +mInterval*4);
                    break;
                case 5:
                    output(5);
                    invalidateGear(mCursorHeight/2 +mInterval*5);
                    break;
                case 6:
                    output(6);
                    invalidateGear(mViewHeight-mCursorHeight/2);
                    break;
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action= event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                float mDownX = event.getX();
                float mDownY = event.getY();
                if (mPointerRegion.contains((int)(mDownX), (int) mDownY)){
                    mSelected = true;
                }else {
                    mSelected = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float mMoveY = event.getY();
                if (mSelected && mMoveY>= mCursorHeight/2 && mMoveY<=mViewHeight- mCursorHeight/2){
                    mCursorY = mMoveY;
                    mRegionPath.addRect(mCursorX-halfWidth-30,mCursorY-halfHeight*3,mCursorX+halfWidth+30,mCursorY+halfHeight*3, Path.Direction.CW);
                    mPointerRegion.setPath(mRegionPath,new Region((int)(mCursorX - halfWidth),(int)(mCursorY - halfHeight),(int)(mCursorX+halfWidth),(int)(mCursorY+halfHeight)));
                    mCursorRect = new RectF(mCursorX - halfWidth, mCursorY - halfHeight, mCursorX + halfWidth, mCursorY + halfHeight);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mSelected){
                    judgeGear(mCursorY);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap temperatureBitmap = drawableToBitmap(getResources().getDrawable(R.drawable.temperature));
        canvas.drawBitmap(temperatureBitmap,null,mRuleRect,mRulePaint);
        Bitmap cursorBitmap = drawableToBitmap(getResources().getDrawable(R.drawable.cursor));
        canvas.drawBitmap(cursorBitmap,null,mCursorRect,mRulePaint);
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
            widthSize = DensityUtil.dp2px(50);
        }
        if (heightMode == MeasureSpec.EXACTLY){
            heightSize = MeasureSpec.getSize(heightMeasureSpec);
        }else if (widthMode == MeasureSpec.AT_MOST){
            heightSize = DensityUtil.dp2px(275);
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = getHeight();
        mViewWidth = getWidth();
        mViewLeft = getLeft();
        mCursorX = mViewWidth>>1;
        mCursorY = mCursorHeight/2;
        mRegionPath = new Path();
        halfWidth = DensityUtil.dp2px(25);
        halfHeight = DensityUtil.dp2px(8);
        mRegionPath.addRect(mCursorX- halfWidth - 30,mCursorY- halfHeight*3,mCursorX+ halfWidth + 30,mCursorY+ halfHeight*3, Path.Direction.CW);
        mPointerRegion = new Region();
        mPointerRegion.setPath(mRegionPath,new Region((int)(mCursorX - halfWidth),(int)(mCursorY - halfHeight),(int)(mCursorX+ halfWidth),(int)(mCursorY+ halfHeight)));
        mInterval = (int) ((mViewHeight- mCursorHeight) / 6);
        mRuleRect = new RectF((mViewWidth >> 1) - mRuleWidth / 2, 0, (mViewWidth >> 1) + mRuleWidth / 2, mViewHeight);
        mCursorRect = new RectF(mCursorX - halfWidth, mCursorY - halfHeight, mCursorX + halfWidth, mCursorY + halfHeight);
    }

    public interface GearListener{
        void onGearSelected(int position);
    }

    public void setGearListener(GearListener gearListener){
        this.mGearListener = gearListener;
    }
}
