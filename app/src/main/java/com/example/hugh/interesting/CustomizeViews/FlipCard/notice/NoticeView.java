package com.example.hugh.interesting.CustomizeViews.FlipCard.notice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.hugh.interesting.R;
import com.example.hugh.interesting.Utils.DensityUtil;

import java.util.List;

/**
 * Created by Hugh on 2020/1/6.
 */
public class NoticeView extends ViewFlipper implements View.OnClickListener {

    private Context mContext;
    private List<String> mNotices;
    private boolean isOdd;

    public NoticeView(Context context) {
        super(context);
        init(context);
    }

    public NoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 添加需要轮播展示的公告
     *
     * @param notices
     */
    public void addNotice(List<String> notices) {
        if (notices.size() > 0) {
            mNotices = notices;
            removeAllViews();
            int size = mNotices.size();
            int numbs = size / 2;
            if (size % 2 != 0) {
                numbs += 1;
                isOdd = true;
            } else {
                isOdd = false;
            }
            for (int i = 0; i < numbs; i++) {
                View itemNotice = View.inflate(mContext, R.layout.item_notice, null);
                TextView tvContent1 = itemNotice.findViewById(R.id.tv_content1);
                TextView tvContent2 = itemNotice.findViewById(R.id.tv_content2);
                if (i == numbs - 1 && isOdd) {
                    tvContent1.setText(mNotices.get(i * 2));
                    tvContent2.setText("");
                } else {
                    tvContent1.setText(mNotices.get(i * 2));
                    tvContent2.setText(mNotices.get(i * 2 + 1));
                }
                //添加到ViewFlipper
                NoticeView.this.addView(itemNotice, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void init(Context context) {
        mContext = context;
        //防止卡顿
        setMeasureAllChildren(false);
        // 轮播间隔时间为3s
        setFlipInterval(3000);
        // 内边距5dp
        setPadding(DensityUtil.dp2px(5f), DensityUtil.dp2px(5f), DensityUtil.dp2px(5f), DensityUtil.dp2px(5f));
        // 设置enter和leave动画
        setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.notice_in));
        setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.notice_out));
    }
}
