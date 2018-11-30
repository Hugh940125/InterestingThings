package com.example.hugh.interesting.keyboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.hugh.interesting.R;
import com.example.hugh.interesting.utils.DensityUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hugh on 2018/11/26.
 *
 */

public class KeyBoard extends View {

    private Paint mPaint;
    private Bitmap mKeyNormalBitmap;
    private Bitmap mKeyPressingBitmap;
    List<Key> mKeyList = new ArrayList<>();
    private List<String> textList;
    private Paint mRegionPaint;
    private Key mClickedKey;
    private Context mContext;
    private OnTextUpdateListener onTextUpdateListener;

    public KeyBoard(Context context) {
        super(context);
        init(context);
    }

    public KeyBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void init(Context context){
        mContext = context;
        mKeyList.clear();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.CYAN);
        mPaint.setTextSize(60);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mRegionPaint = new Paint();
        mRegionPaint.setAntiAlias(true);
        mRegionPaint.setStyle(Paint.Style.FILL);
        mRegionPaint.setColor(Color.WHITE);
        Drawable key_normal = getResources().getDrawable(R.drawable.key_normal,null);
        mKeyNormalBitmap = drawableToBitmap(key_normal);
        Drawable key_pressing = getResources().getDrawable(R.drawable.key_pressing,null);
        mKeyPressingBitmap = drawableToBitmap(key_pressing);

        String[] texts = {"1","2","3","4","5","6","7","8","9","0"};
        textList = Arrays.asList(texts);
        Collections.shuffle(textList);
        //mKeyList = getKeys();
    }

    @Nullable
    private List<Key> getKeys() {
        int width = getWidth();
        int height = getHeight();
        int keyWidth = width / 3;
        int keyHeight = height / 4;
        ArrayList<Key> keyList = new ArrayList<>();
        for (int i=0;i<12;i++){
            if (i<3){
                int x = keyWidth/2 + i*keyWidth;
                int y = keyHeight/2;
                Rect region = new Rect(x - keyWidth / 2, y - keyHeight / 2, x + keyWidth / 2, y + keyHeight / 2);
                Key key = new Key(x, y, Key.STATE_NORMAL, textList.get(i),region);
                keyList.add(key);
            }else if (i>=3 && i<6){
                int x = keyWidth/2 + (i-3)*keyWidth;
                int y = keyHeight*3/2;
                Rect region = new Rect(x - keyWidth / 2, y - keyHeight / 2, x + keyWidth / 2, y + keyHeight / 2);
                Key key = new Key(x, y, Key.STATE_NORMAL, textList.get(i),region);
                keyList.add(key);
            }else if (i>=6 && i<9){
                int x = keyWidth/2 + (i-6)*keyWidth;
                int y = keyHeight*5/2;
                Rect region = new Rect(x - keyWidth / 2, y - keyHeight / 2, x + keyWidth / 2, y + keyHeight / 2);
                Key key = new Key(x, y, Key.STATE_NORMAL, textList.get(i),region);
                keyList.add(key);
            }else if (i>=9 && i<12) {
                if (i==9){
                    int x = keyWidth/2 + (i-9)*keyWidth;
                    int y = keyHeight*7/2;
                    Rect region = new Rect(x - keyWidth / 2, y - keyHeight / 2, x + keyWidth / 2, y + keyHeight / 2);
                    Key key = new Key(x, y, Key.STATE_NORMAL, textList.get(i), region);
                    keyList.add(key);
                }else if (i==10){
                    int x = keyWidth/2 + (i-9)*keyWidth;
                    int y = keyHeight*7/2;
                    Rect region = new Rect(x - keyWidth / 2, y - keyHeight / 2, x + keyWidth / 2, y + keyHeight / 2);
                    Key key = new Key(x, y, Key.STATE_NORMAL, "删除", region);
                    keyList.add(key);
                }else {
                    int x = keyWidth/2 + (i-9)*keyWidth;
                    int y = keyHeight*7/2;
                    Rect region = new Rect(x - keyWidth / 2, y - keyHeight / 2, x + keyWidth / 2, y + keyHeight / 2);
                    Key key = new Key(x, y, Key.STATE_NORMAL, "确定", region);
                    keyList.add(key);
                }
            }
        }
        return keyList;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = 0;
        int heightSize = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY){
            widthSize = MeasureSpec.getSize(widthMeasureSpec);
        }else if (widthMode == MeasureSpec.AT_MOST){
            widthSize = DensityUtil.dp2px(200);
        }
        if (heightMode == MeasureSpec.EXACTLY){
            heightSize = MeasureSpec.getSize(heightMeasureSpec);
        }else if (widthMode == MeasureSpec.AT_MOST){
            heightSize = DensityUtil.dp2px(200);
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mKeyList = getKeys();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawKeys(mKeyList,canvas);
    }

    private void drawKeys(List<Key> keys, Canvas canvas) {
        for (Key key:keys){
            if (key.state == Key.STATE_NORMAL){
                canvas.drawBitmap(mKeyNormalBitmap,key.region,key.region,mRegionPaint);
            }else {
                canvas.drawBitmap(mKeyPressingBitmap,key.region,key.region,mRegionPaint);
            }
            String text = key.text;
            Rect rect = new Rect();
            mPaint.getTextBounds(text,0,text.length(),rect);
            canvas.drawText(key.text,key.x,key.y + rect.height()/2,mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                for (Key key:mKeyList){
                    if (key.region.contains(x,y)){
                        key.state = Key.STATE_PRESSING;
                        mClickedKey = key;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int xUp = (int) event.getX();
                int yUp = (int) event.getY();
                if (mClickedKey != null){
                    mClickedKey.state = Key.STATE_NORMAL;
                    invalidate();
                    if (!mClickedKey.region.contains(xUp,yUp)){
                        mClickedKey = null;
                    }else {
                        //Toast.makeText(mContext, mClickedKey.text, Toast.LENGTH_SHORT).show();
                        if (onTextUpdateListener != null){
                            onTextUpdateListener.OnTextUpdate(mClickedKey.text);
                        }
                    }
                }
                break;
        }
        return true;
    }

    public interface OnTextUpdateListener{
        void OnTextUpdate(String text);
    }

    public void addOnTextUpdateListener(OnTextUpdateListener onTextUpdateListener){
        this.onTextUpdateListener = onTextUpdateListener;
    }

    public static class Key{
        private int x;
        private int y;
        private String text;
        public static final int STATE_NORMAL = 0;
        public static final int STATE_PRESSING = 1;
        public int state = 0;
        public Rect region;

        public Key(int x, int y, int state, String text,Rect region) {
            this.x = x;
            this.y = y;
            this.text = text;
            this.state = state;
            this.region = region;
        }

        @Override
        public String toString() {
            return "Key{" +
                    "x=" + x +
                    ", y=" + y +
                    ", text='" + text + '\'' +
                    ", state=" + state +
                    '}';
        }
    }
}
