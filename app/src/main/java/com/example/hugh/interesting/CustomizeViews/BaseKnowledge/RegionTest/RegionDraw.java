package com.example.hugh.interesting.CustomizeViews.BaseKnowledge.RegionTest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Hugh on 2021/1/8.
 */

class RegionDraw extends View {

    private Region mRegion;
    private Paint mPaint;

    public RegionDraw(Context context) {
        super(context);
    }

    public RegionDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(){
        mRegion = new Region();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        RectF rect = new RectF(50,50,200,500);
        path.addOval(rect, Path.Direction.CCW);
        mRegion.setPath(path,new Region(50,50,200,200));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRegion(mRegion,canvas);
    }

    private void drawRegion(Region region,Canvas canvas){
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect = new Rect();
        while(regionIterator.next(rect)){
            canvas.drawRect(rect,mPaint);
        }
    }
}
