package com.example.hugh.interesting.RecyclerView.refresh_layout;

import android.view.View;

/**
 * Created by Hugh on 2018/12/7.
 *
 */

public interface IBottomView {
    View getView();

    /**
     * 上拉准备加载更多的动作
     *
     * @param fraction      上拉高度与Bottom总高度之比
     * @param maxBottomHeight 底部部可拉伸最大高度
     * @param bottomHeight    底部高度
     */
    void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight);

    void startAnim(float maxBottomHeight, float bottomHeight);

    /**
     * 上拉释放过程
     */
    void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight);

    void onFinish();

    void reset();
}
