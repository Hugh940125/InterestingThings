package com.example.hugh.interesting.CustomizeViews.HousePlan;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.example.hugh.interesting.Utils.DensityUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugh on 2018/11/6.
 */

public class HousePlanView extends ViewGroup {

    private Context mContext;
    private int mMode = 0;
    private int lastX;
    private int lastY;
    private Rect rect;
    private int mSelectedView = -1;
    private List<UserRoomXYSh> mHomeTagList = new ArrayList<>();
    final float TAG_WIDTH_SCALE = 5.0F;
    final float TAG_HEIGHT_SCALE = 5.0F;
    private Rect rectTag;
    private OnTagClickListener onTagClickListener;
    private OnTagDeleteListener onTagDeleteListener;
    private OnTagRecyclingListener onTagRecyclingListener;

    public HousePlanView(Context context) {
        super(context);
        init(context);
    }

    public HousePlanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    /**
     * if mode is 0,this view is only for display ,otherwise you can edit the tags
     *
     * @param mode
     */
    public void setMode(int mode) {
        this.mMode = mode;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            ((CustomTextView) child).setMode(mode);
        }
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int childCount = getChildCount();
        int action = event.getAction();
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mMode == 0) {
                    int x1 = (int) event.getX();
                    int y1 = (int) event.getY();
                    // to identify childViews' delete region
                    for (int i = 0; i < childCount; i++) {
                        View child = getChildAt(i);
                        Rect deleteRect = new Rect(child.getRight() - 60, child.getTop(), child.getRight(), child.getTop() + 60);
                        if (deleteRect.contains(x1, y1)) {
                            TagDeleteCallback tagDeleteCallback = new TagDeleteCallback() {
                                @Override
                                public void onConfirm(View view) {
                                    removeView(view);
                                }

                                @Override
                                public void onCancel() {

                                }
                            };
                            if (onTagDeleteListener != null){
                                onTagDeleteListener.OnTagDelete(child, tagDeleteCallback);
                            }
                            break;
                        }
                    }
                    // to identify which childView clicked
                    childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = getChildAt(i);
                        Rect rect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
                        if (rect.contains(x1, y1)) {
                            mSelectedView = i;
                            child.bringToFront();
                        }
                    }

                    lastX = x;
                    lastY = y;
                    int left = getLeft();
                    int top = getTop();
                    int right = getRight();
                    int bottom = getBottom();
                    rect = new Rect(left, top, right, bottom);
                } else {
                    int x1 = (int) event.getX();
                    int y1 = (int) event.getY();
                    childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = getChildAt(i);
                        rectTag = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
                        if (rectTag.contains(x1, y1)) {
                            mSelectedView = i;
                            child.bringToFront();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mMode == 0) {
                    if (onTagRecyclingListener != null){
                        onTagRecyclingListener.onTagMoved(true);
                    }
                    if (mSelectedView != -1) {
                        int offsetX = x - lastX;
                        int offsetY = y - lastY;
                        Rect rect1 = new Rect(getChildAt(mSelectedView).getLeft() + offsetX,
                                getChildAt(mSelectedView).getTop() + offsetY,
                                getChildAt(mSelectedView).getRight() + offsetX,
                                getChildAt(mSelectedView).getBottom() + offsetY);
                        // to ensure the tags is contained in the parent view
                        if (this.rect.contains(rect1)) {
                            View childAt = getChildAt(mSelectedView);
                            // to update tag's position
                            childAt.layout(getChildAt(mSelectedView).getLeft() + offsetX,
                                    getChildAt(mSelectedView).getTop() + offsetY,
                                    getChildAt(mSelectedView).getRight() + offsetX,
                                    getChildAt(mSelectedView).getBottom() + offsetY);
                        }
                        lastX = x;
                        lastY = y;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                int xUp = (int) event.getX();
                int yUp = (int) event.getY();
                if (null != rect) {
                    int bottom = rect.bottom - DensityUtil.dp2px(3);
                    View childAt = getChildAt(mSelectedView);
                    if (childAt != null && childAt.getBottom() >= bottom) {
                        TagRecyclingCallback tagRecyclingCallback = new TagRecyclingCallback() {
                            @Override
                            public void onConfirm(View view) {
                                removeView(view);
                                mSelectedView = -1;
                            }

                            @Override
                            public void onCancel() {

                            }
                        };
                        if (onTagRecyclingListener != null){
                            onTagRecyclingListener.OnTagRecycling(childAt, tagRecyclingCallback);
                        }
                    }
                }
                if (getChildCount() > 0) {
                    if (onTagClickListener != null && mMode != 0 && rectTag != null && rectTag.contains(xUp, yUp) && mSelectedView != -1) {
                        onTagClickListener.onTagClick(getChildAt(mSelectedView));
                    }
                    mSelectedView = -1;
                }
                break;
        }
        return true;
    }

    public interface OnTagClickListener {
        void onTagClick(View view);
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public interface OnTagDeleteListener {
        void OnTagDelete(View view, TagDeleteCallback tagDeleteCallback);
    }

    public void setOnTagDeleteListener(OnTagDeleteListener onTagDeleteListener) {
        this.onTagDeleteListener = onTagDeleteListener;
    }

    public interface OnTagRecyclingListener {
        void OnTagRecycling(View view, TagRecyclingCallback tagRecyclingCallback);

        void onTagMoved(boolean moved);
    }

    public void setOnTagRecyclingListener(OnTagRecyclingListener onTagRecyclingListener) {
        this.onTagRecyclingListener = onTagRecyclingListener;
    }

    public void addRoom(final UserRoomInfoSh userRoomInfoSh, final int floorPosition, final double xScale, final double yScale) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TagView tagView = new TagView(mContext);
                tagView.setMode(mMode);
                int height = getHeight();
                int width = getWidth();
                int childWidth = (int) (getWidth() / TAG_WIDTH_SCALE);
                int childHeight = (int) (getHeight() / TAG_HEIGHT_SCALE);
                LayoutParams layoutParams = new LayoutParams(childWidth, childHeight);
                tagView.setLayoutParams(layoutParams);
                tagView.setRoomId(userRoomInfoSh.getId());
                tagView.setRoomFloor(floorPosition + "");
                tagView.setRoomName(userRoomInfoSh.getRoomName());
                tagView.setText(userRoomInfoSh.getRoomName());
                if (xScale == 0 && yScale == 0) {
                    tagView.layout(width / 2 - childWidth / 2, height / 2 - childHeight / 2, width / 2 + childWidth / 2, height / 2 + childHeight / 2);
                } else {
                    int left = (int) (width * xScale - childWidth / 2);
                    int top = (int) (height * yScale - childHeight / 2);
                    int right = (int) (width * xScale + childWidth / 2);
                    int bottom = (int) (height * yScale + childHeight / 2);
                    tagView.layout(left, top, right, bottom);
                }
                addView(tagView);
            }
        }, 100);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
    }

    public List<UserRoomXYSh> saveRoomTags() {
        mHomeTagList.clear();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int left = childAt.getLeft();
            int top = childAt.getTop();
            String roomName = ((TagView) childAt).getRoomName();
            String roomId = ((TagView) childAt).getRoomId();
            String floor = ((TagView) childAt).getRoomFloor();
            int height = childAt.getHeight();
            int width = childAt.getWidth();
            double x = left + width / 2;
            double y = top + height / 2;
            double scaleX = x / getWidth();
            double scaleY = y / getHeight();
            DecimalFormat decimalFormat = new DecimalFormat("0.0000");
            String XFormat = decimalFormat.format(scaleX);
            String YFormat = decimalFormat.format(scaleY);
            UserRoomXYSh userRoomXYSh = new UserRoomXYSh();
            userRoomXYSh.setRoomName(roomName);
            userRoomXYSh.setRoomX(XFormat);
            userRoomXYSh.setRoomY(YFormat);
            userRoomXYSh.setRoomId(roomId);
            userRoomXYSh.setRoomFloor(floor);
            mHomeTagList.add(userRoomXYSh);
        }
        Log.e("result", mHomeTagList.toString());
        return mHomeTagList;
    }
}
