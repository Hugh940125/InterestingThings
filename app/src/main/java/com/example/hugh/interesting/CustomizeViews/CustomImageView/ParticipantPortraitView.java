package com.example.hugh.interesting.CustomizeViews.CustomImageView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hugh.interesting.R;

import java.util.List;

/**
 * Created by Hugh on 2019/9/4.
 */
public class ParticipantPortraitView extends ViewGroup {

    private Context mContext;

    public ParticipantPortraitView(Context context) {
        super(context);
        this.mContext = context;
    }

    public ParticipantPortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setData(List<String> urls) throws Exception {
        if (urls==null){
            throw new Exception("Illegal parameter");
        }else if (urls.size() != 3){
            throw new Exception("Illegal parameter");
        }else {
            int childCount = getChildCount();
            for (int i=childCount-1;i>=0;i--){
                ImageView childAt = (ImageView) getChildAt(i);
            }
        }
    }

    private void init(Context context) {
        CircleImageView niceImageView1 = new CircleImageView(context);
        niceImageView1.setImageDrawable(getResources().getDrawable(R.drawable.punched));
        niceImageView1.setLayoutParams(new LayoutParams(getWidth()/2,getHeight()));
        niceImageView1.layout(0,0,getWidth()/2,getHeight());
        CircleImageView niceImageView2 = new CircleImageView(context);
        niceImageView2.setImageDrawable(getResources().getDrawable(R.drawable.punched));
        niceImageView2.setLayoutParams(new LayoutParams(getWidth()/2,getHeight()));
        niceImageView2.layout(getWidth()/4,0,getWidth()*3/4,getHeight());
        CircleImageView niceImageView3 = new CircleImageView(context);
        niceImageView3.setImageDrawable(getResources().getDrawable(R.drawable.punched));
        niceImageView3.setLayoutParams(new LayoutParams(getWidth()/2,getHeight()));
        niceImageView3.layout(getWidth()/2,0,getWidth(),getHeight());
        addView(niceImageView3);
        addView(niceImageView2);
        addView(niceImageView1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        init(mContext);
//        getChildAt(0).layout(0,0,getWidth()/3,getHeight());
//        getChildAt(1).layout(getWidth()/3,0,getWidth()*2/3,getHeight());
//        getChildAt(2).layout(getWidth()*2/3,0,getWidth(),getHeight());
    }
}
