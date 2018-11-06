package com.example.hugh.tests.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class ElasticRecyclerView extends RecyclerView {

    private final int MAX_OVER_SCROLL_Y = 100;

    public ElasticRecyclerView(Context context) {
        super(context);
    }

    public ElasticRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY,
                                  scrollX, scrollY,
                                  scrollRangeX, scrollRangeY,
                                  maxOverScrollX, MAX_OVER_SCROLL_Y, //修改此参数可以使listview下拉时有弹性
                                  isTouchEvent);
    }
}