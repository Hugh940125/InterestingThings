package com.example.hugh.interesting.CustomizeViews.HousePlan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.example.hugh.interesting.R;

/**
 * Created by Hugh on 2018/12/6.
 *
 */

public class TagView extends View {

    private Context mContext;
    private Bitmap bitmap;
    private String mText = "名称";
    private String roomId;
    private String roomName;
    private String roomX;
    private String roomY;
    private String roomFloor;
    private int mode = 0;

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomX() {
        return roomX;
    }

    public void setRoomX(String roomX) {
        this.roomX = roomX;
    }

    public String getRoomY() {
        return roomY;
    }

    public void setRoomY(String roomY) {
        this.roomY = roomY;
    }

    public String getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(String roomFloor) {
        this.roomFloor = roomFloor;
    }

    public TagView(Context context) {
        this(context, null);
    }

    public TagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Drawable drawable = context.getResources().getDrawable(R.drawable.tag_delete);
        bitmap = drawableToBitmap(drawable);
        mContext = context;
    }

    public void setText(String str) {
        this.mText = str;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Paint imgPaint = new Paint();
        imgPaint.setAntiAlias(true);
        Paint paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setTextAlign(Paint.Align.CENTER);
        paint1.setTextSize(sp2px(mContext, 12));
        paint.setColor(Color.BLACK);
        paint.setAlpha(125);
        int height = getHeight();
        int width = getWidth();
        RectF rectF = new RectF(0, (float) (height * 0.23), (float) (width * 0.85), height);
        canvas.drawRoundRect(rectF, 10, 10, paint);
        if (mode == 0) {
            float a = 0.325F;
            float b = (float)(Math.round(a*10*3))/(10*3);
            canvas.drawBitmap(bitmap, null, new RectF((float) (width * 0.75), 0, width, (float) (height * b)), imgPaint);
        }
        Rect rect = new Rect();
        if (mText.length() > 3) {
            mText = mText.substring(0, 3) + "..";
        }
        paint1.getTextBounds(mText, 0, mText.length(), rect);
        canvas.drawText(mText, (float) (width * 0.43), (float) (height * 0.62 + rect.height() / 2), paint1);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }
}
