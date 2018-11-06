package com.example.hugh.tests.Views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hugh on 2018/11/6.
 *
 */

public class HousePlanView extends ViewGroup {

    private int lastX;
    private int lastY;
    private Rect rect;
    private int mSelectedView = -1;

    public HousePlanView(Context context) {
        super(context);
    }

    public HousePlanView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int mTotalHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();

            childView.layout(l, mTotalHeight, measuredWidth, mTotalHeight
                    + measureHeight);
            mTotalHeight += measureHeight;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("size",width+"--"+height);
        setMeasuredDimension(width,height);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int childCount = getChildCount();
        int action = event.getAction();
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                int x1 = (int) event.getX();
                int y1 = (int) event.getY();

                for (int i=0;i<childCount;i++){
                    View child = getChildAt(i);
                    Rect deleteRect = new Rect(child.getLeft()+child.getWidth()-20, child.getTop()+20, child.getRight(), child.getBottom());
                    if (deleteRect.contains(x1,y1)){
                        removeViewAt(i);
                        break;
                    }
                }
                childCount = getChildCount();
                for (int i=0;i<childCount;i++){
                    View child = getChildAt(i);
                    Rect rect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
                    Rect deleteRect = new Rect(child.getLeft()+child.getWidth()-20, child.getTop()+20, child.getRight(), child.getBottom());
                    if (rect.contains(x1,y1)){
                        mSelectedView = i;
                    }
                    if (deleteRect.contains(x1,y1)){
                        removeViewAt(i);
                    }
                }

                lastX = x;
                lastY = y;
                int left = getLeft();
                int top = getTop();
                int right = getRight();
                int bottom = getBottom();
                rect = new Rect(left , top , right , bottom );
                break;
            case MotionEvent.ACTION_MOVE:
                if (mSelectedView != -1){
                    int offsetX = x - lastX;
                    int offsetY = y - lastY;
                    Rect rect1 = new Rect(getChildAt(mSelectedView).getLeft() + offsetX, getChildAt(mSelectedView).getTop() + offsetY,
                            getChildAt(mSelectedView).getRight() + offsetX,
                            getChildAt(mSelectedView).getBottom() + offsetY);
                    Log.e("size",rect.toString()+"-|-"+rect1.toString());
                    if (this.rect.contains(rect1)){
                        getChildAt(mSelectedView).layout(
                                getChildAt(mSelectedView).getLeft() + offsetX,
                                getChildAt(mSelectedView).getTop() + offsetY,
                                getChildAt(mSelectedView).getRight() + offsetX,
                                getChildAt(mSelectedView).getBottom() + offsetY);
                    }
                    lastX = x;
                    lastY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                mSelectedView = -1;
                break;
        }
        return true;
    }

}
