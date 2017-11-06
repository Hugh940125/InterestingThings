package com.example.hugh.tests.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ElasticListView extends ListView {

    private final int MAX_OVER_SCROLL_Y = 100;

    public ElasticListView(Context context) {
        super(context);
    }

    public ElasticListView(Context context, AttributeSet attrs) {
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