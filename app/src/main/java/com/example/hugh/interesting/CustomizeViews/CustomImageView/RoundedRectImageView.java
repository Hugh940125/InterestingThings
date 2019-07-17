package com.example.hugh.interesting.CustomizeViews.CustomImageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.example.hugh.interesting.R;
import com.example.hugh.interesting.Utils.DensityUtil;

/**
 * Created by Hugh on 2019/7/12.
 */
public class RoundedRectImageView extends android.support.v7.widget.AppCompatImageView {
    private int roundWidth;
    private int roundHeight;
    private Paint paint;
    private Paint paint2;
    private Canvas canvas2;
    private Bitmap bitmap;
    private int roundedCorners;

    public RoundedRectImageView(Context context) {
        this(context, null);
    }

    public RoundedRectImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedRectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedRectImageView);
        roundedCorners = typedArray.getInt(R.styleable.RoundedRectImageView_roundedCorners, 0);
        typedArray.recycle();

        roundWidth = DensityUtil.dp2px(8);
        roundHeight = DensityUtil.dp2px(8);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint2 = new Paint();
        paint2.setXfermode(null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas2 = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (roundedCorners == 1) {
            drawLeftUp(canvas2);
            drawLeftBottom(canvas2);
        }
        if (roundedCorners == 2) {
            drawRightUp(canvas2);
            drawRightBottom(canvas2);
        }
        if (roundedCorners == 3) {
            drawLeftUp(canvas2);
            drawRightUp(canvas2);
        }
        if (roundedCorners == 4) {
            drawLeftBottom(canvas2);
            drawRightBottom(canvas2);
        }
        if (roundedCorners == 0) {
            drawLeftUp(canvas2);
            drawRightUp(canvas2);
            drawLeftBottom(canvas2);
            drawRightBottom(canvas2);
        }
        canvas.drawBitmap(bitmap, 0, 0, paint2);
        bitmap.recycle();
    }


    private void drawLeftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, roundHeight);
        path.lineTo(0, 0);
        path.lineTo(roundWidth, 0);
        path.arcTo(new RectF(0, 0, roundWidth * 2, roundHeight * 2), -90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), roundHeight);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - roundWidth, 0);
        path.arcTo(new RectF(getWidth() - roundWidth * 2, 0, getWidth(), roundHeight * 2), -90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLeftBottom(Canvas canvas) {
        Path path = new Path();
        path.moveTo(roundWidth, getHeight());
        path.lineTo(0, getHeight());
        path.lineTo(0, getHeight() - roundHeight);
        path.arcTo(new RectF(0, getHeight() - roundHeight * 2, roundWidth * 2, getHeight()), 90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightBottom(Canvas canvas) {
        Path path = new Path();
        path.moveTo(roundWidth, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth() - roundHeight, getHeight() - roundHeight);
        path.arcTo(new RectF(getWidth() - roundWidth * 2, getHeight() - roundHeight * 2, roundWidth * 2, getHeight()), 90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }
}
