package com.example.hugh.interesting.CustomizeViews.FlipCard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

/**
 * Created by Hugh on 2019/11/28.
 */
public class FlipCardVIew extends RelativeLayout {

    private OnFlipListener onFlipListener;

    public FlipCardVIew(Context context) {
        super(context);
    }

    public FlipCardVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int childWidth = 0;
        int childHeight = 0;
        int cCount = getChildCount();
        MarginLayoutParams cParams;
        /**
         * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
         */
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cParams = (MarginLayoutParams) childView.getLayoutParams();
            childWidth = childView.getMeasuredWidth() + cParams.leftMargin + cParams.rightMargin;
            childHeight = childView.getMeasuredHeight() + cParams.topMargin + cParams.bottomMargin;
        }
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : childWidth, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : childHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childW = childView.getMeasuredWidth();
            int childH = childView.getMeasuredHeight();
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
            childView.layout((measuredWidth >> 1) - (childW >> 1) + layoutParams.leftMargin, (measuredHeight >> 1) - (childH >> 1) + layoutParams.topMargin, (measuredWidth >> 1) + (childW >> 1) - layoutParams.rightMargin, (measuredHeight >> 1) + (childH >> 1) - layoutParams.bottomMargin);
        }
    }

    public void start(boolean flip) {
        startAnimation(getChildAt(0), 180, flip);
    }

    private void startAnimation(View view, int degree, boolean flip) {
        int width = view.getWidth() / 2;
        int height = view.getHeight() / 2;
        FlipCardAnimation animation = new FlipCardAnimation(0, degree, width, height, flip);
        //animation.setInterpolator(new AnticipateOvershootInterpolator());
        animation.setDuration(15000);
        animation.setFillAfter(false);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setStartOffset(5000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                ((FlipCardAnimation) animation).setCanContentChange();
            }
        });
        animation.setOnContentChangeListener(() -> {
            if (onFlipListener != null) {
                onFlipListener.onFlip();
            }
        });
        view.startAnimation(animation);
    }

    public interface OnFlipListener {
        void onFlip();
    }

    public void setOnFlipListener(OnFlipListener onFlipListener) {
        this.onFlipListener = onFlipListener;
    }
}
