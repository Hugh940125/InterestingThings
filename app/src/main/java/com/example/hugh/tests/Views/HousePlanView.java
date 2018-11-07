package com.example.hugh.tests.Views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hugh on 2018/11/6.
 *
 */

public class HousePlanView extends ViewGroup {

    private Context mContext;
    private int lastX;
    private int lastY;
    private Rect rect;
    private int mSelectedView = -1;
    ArrayList<HashMap> mHomeTagList = new ArrayList<>();
    final float TAG_WIDTH_SCALE = 6.0F;
    final float TAG_HEIGHT_SCALE = 5.0F;

    public HousePlanView(Context context) {
        super(context);
    }

    public HousePlanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("size",width+"--"+height);
        setMeasuredDimension(width,height);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int childCount = getChildCount();
        int action = event.getAction();
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                int x1 = (int) event.getX();
                int y1 = (int) event.getY();

                for (int i=0;i<childCount;i++){
                    View child = getChildAt(i);
                    Rect deleteRect = new Rect(child.getLeft()+child.getWidth()-50, child.getTop()+50, child.getRight(), child.getBottom());
                    if (deleteRect.contains(x1,y1)){
                        removeViewAt(i);
                        break;
                    }
                }
                childCount = getChildCount();
                for (int i=0;i<childCount;i++){
                    View child = getChildAt(i);
                    Rect rect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
                    Rect deleteRect = new Rect(child.getLeft()+child.getWidth()-50, child.getTop()+50, child.getRight(), child.getBottom());
                    if (rect.contains(x1,y1)){
                        mSelectedView = i;
                        child.bringToFront();
                    }
                    if (deleteRect.contains(x1,y1)){
                        removeViewAt(i);
                    }
                }

                lastX = x;
                lastY = y;
                int left = getLeft();
                int top = getTop();
                int right = getRight();
                int bottom = getBottom();
                rect = new Rect(left , top , right , bottom );
                break;
            case MotionEvent.ACTION_MOVE:
                if (mSelectedView != -1){
                    int offsetX = x - lastX;
                    int offsetY = y - lastY;
                    Rect rect1 = new Rect(getChildAt(mSelectedView).getLeft() + offsetX,
                            getChildAt(mSelectedView).getTop() + offsetY,
                            getChildAt(mSelectedView).getRight() + offsetX,
                            getChildAt(mSelectedView).getBottom() + offsetY);
                    Log.e("size",rect.toString()+"-|-"+rect1.toString());
                    if (this.rect.contains(rect1)){
                        View childAt = getChildAt(mSelectedView);
                        childAt.layout(
                                getChildAt(mSelectedView).getLeft() + offsetX,
                                getChildAt(mSelectedView).getTop() + offsetY,
                                getChildAt(mSelectedView).getRight() + offsetX,
                                getChildAt(mSelectedView).getBottom() + offsetY);
                    }
                    lastX = x;
                    lastY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                mSelectedView = -1;
                break;
        }
        return true;
    }

    public void addRoom(String name,double xScale,double yScale){
        TextView textView = new TextView(mContext);
        int height = getHeight();
        int width = getWidth();
        int childWidth = (int) (getWidth() / TAG_WIDTH_SCALE);
        int childHeight = (int) (getHeight() / TAG_HEIGHT_SCALE);
        LayoutParams layoutParams = new LayoutParams(childWidth, childHeight);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.CYAN);
        textView.setText(name);
        if (xScale == 0 && yScale == 0){
            textView.layout(width/2-childWidth/2,height/2-childHeight/2,width/2+childWidth/2,height/2+childHeight/2);
        }else {
            int left = (int) (width * xScale - childWidth / 2);
            int top = (int) (height * yScale - childHeight / 2);
            int right = (int) (width * xScale + childWidth / 2);
            int bottom = (int) (height * yScale + childHeight / 2);
            textView.layout(left,top,right,bottom);
        }
        addView(textView);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);

    }

    public void saveRoomTags(){
        mHomeTagList.clear();
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View childAt = getChildAt(i);
            int left = childAt.getLeft();
            int top = childAt.getTop();
            String roomName = ((TextView) childAt).getText().toString();
            int height = childAt.getHeight();
            int width = childAt.getWidth();
            double x = left + width / 2;
            double y = top + height / 2;
            double scaleX = x/getWidth();
            double scaleY = y/getHeight();
            DecimalFormat decimalFormat = new DecimalFormat("0.0000");
            String XFormat = decimalFormat.format(scaleX);
            String YFormat = decimalFormat.format(scaleY);
            Log.e("result",x+","+y+","+XFormat+","+YFormat+"-"+roomName);
            HashMap<String, String> tagMap = new HashMap<>();
            tagMap.put("roomName",roomName);
            tagMap.put("xScale",XFormat);
            tagMap.put("yScale",YFormat);
            mHomeTagList.add(tagMap);
        }
        Log.e("result",mHomeTagList.toString());
    }
}
