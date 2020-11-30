package com.example.hugh.interesting.CustomizeViews.FlipCard;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Hugh on 2019/11/28.
 */
public class FlipCardAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final boolean mFlip;
    private Camera mCamera;
    //用于确定内容是否开始变化
    private boolean isContentChange = false;
    private OnContentChangeListener listener;

    public FlipCardAnimation(float fromDegrees, float toDegrees, float centerX, float centerY,boolean flip) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mFlip = flip;
    }

    ////用于确定内容是否开始变化  在动画开始之前调用
    public void setCanContentChange() {
        this.isContentChange = false;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {

        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (mFlip){
            final float fromDegrees = mFromDegrees;
            float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final Matrix matrix = t.getMatrix();
            camera.save();
            if (degrees > 90 || degrees < -90) {
                if (!isContentChange) {
                    if (listener != null) {
                        listener.contentChange();
                    }
                    isContentChange = true;
                }
                //当View旋转到平行于水平面时，将View变化180度，防止旋转后内容倒置
                if (degrees > 0) {
                    //顺时针旋转
                    degrees = degrees+180;
                } else if (degrees < 0) {
                    //逆时针旋转
                    //degrees = degrees-180;
                }
            }
            camera.rotateX(degrees);
            camera.getMatrix(matrix);
            camera.restore();
            // 我们都知道，在2D中，不论是旋转，错切还是缩放都是能够指定操作中心点位置的，
            // 但是在3D中却没有默认的方法，如果我们想要让图片围绕中心点旋转怎么办?
            // 这就要使用到我们在Matrix原理提到过的方法:
            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }

    public void setOnContentChangeListener(OnContentChangeListener listener) {
        this.listener = listener;
    }

    public interface OnContentChangeListener {
        void contentChange();
    }
}

