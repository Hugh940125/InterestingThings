package com.example.hugh.interesting.CustomizeViews.filter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by Hugh on 2019/6/19.
 */
public class TextViewWithUnderLine extends android.support.v7.widget.AppCompatTextView {

    private Paint mCheckedPaint;
    private boolean isChecked;
    private Rect rect;

    public TextViewWithUnderLine(Context context) {
        this(context, null);
    }

    public TextViewWithUnderLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCheckedPaint = new Paint();
        mCheckedPaint.setAntiAlias(true);
        mCheckedPaint.setColor(Color.parseColor("#5076ED"));
        mCheckedPaint.setStyle(Paint.Style.FILL);
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getWidth();
        rect = new Rect((width >> 1) - 40, getHeight() - 10, (width >> 1) + 40, getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isChecked) {

        }
        canvas.drawRect(rect, mCheckedPaint);
    }

    public void setStatus(boolean isChecked) {
        this.isChecked = isChecked;
        invalidate();
    }
}
