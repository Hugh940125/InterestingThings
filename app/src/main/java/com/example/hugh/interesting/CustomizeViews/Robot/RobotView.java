package com.example.hugh.interesting.CustomizeViews.Robot;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hugh.interesting.HyperLink.HyperLinkText;
import com.example.hugh.interesting.R;
import com.example.hugh.interesting.Utils.CheckPermissionUtils;
import com.example.hugh.interesting.Utils.DensityUtil;
import com.example.hugh.interesting.Utils.PermissionCallBack;
import com.example.hugh.interesting.Utils.SharedPreferencesUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Hugh on 2018/12/11.
 */

public class RobotView extends ViewGroup {

    private int mViewHeight;
    private int mViewWidth;
    private int mViewLeft;
    private int mViewRight;
    private int mViewBottom;
    private Context mContext;
    private Rect mFrameRect;
    private int lastX;
    private int lastY;
    private Rect childRect;
    private View childAt;
    boolean isConsume = false;
    private boolean isFirst = true;
    private boolean isLongClick;
    private Timer timer;
    private float XAnimatedValue;
    private float YAnimatedValue;
    private int moveX;
    private int moveY;
    private int downX;
    private int downY;
    private TimerHandler mHandler = new TimerHandler(RobotView.this);
    private boolean isControlWindowShowing = false;
    private Rect dismissRect;
    private boolean isDialogWindowShow = false;
    private boolean isInMotion = false;
    private OnSpeakListener onSpeakListener;
    private boolean isMoving = false;
    private long lastClickTime;
    public boolean isAppearing;
    private Runnable mStoredRunnable;
    private final int ROBOT_WIDTH = 120;
    private final int ROBOT_HEIGHT = 120;
    private Runnable runnable;
    String[] mTips = new String[]{"我是小冶，一天也不耽误，一天也不懈怠", "你好像还没有登录，赶快登录或注册吧", "赶快绑定房间吧", "您家还没有智能家居呢，赶快添加设备吧", "点我的头可以进行语音操作哦"};

    public RobotView(Context context) {
        super(context);
        init(context);
    }

    public RobotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mViewHeight = getHeight();
        mViewWidth = getWidth();
        mViewLeft = getLeft();
        int mViewTop = getTop();
        mViewRight = getRight();
        mViewBottom = getBottom();
        mFrameRect = new Rect(mViewLeft, mViewTop, mViewRight, mViewBottom);
        if (isFirst) {
            if (getChildCount() > 0) {
                removeAllViews();
                addViews();
            } else {
                addViews();
            }
            isFirst = false;
        }
    }

    private void addViews() {
        LinearLayout mWindowFrame = new LinearLayout(mContext);
        LayoutParams windowParams = new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dp2px(250));
        mWindowFrame.setLayoutParams(windowParams);
        mWindowFrame.setVisibility(INVISIBLE);
        mWindowFrame.setBackground(mContext.getResources().getDrawable(R.drawable.control_dialog_bg));
        mWindowFrame.layout(mViewLeft, mViewBottom - DensityUtil.dp2px(250), mViewRight, mViewBottom);
        addView(mWindowFrame, 0);

        ImageView Iv_cancel = new ImageView(mContext);
        LayoutParams cancelParams = new LayoutParams(DensityUtil.dp2px(30), DensityUtil.dp2px(30));
        Iv_cancel.setLayoutParams(cancelParams);
        Iv_cancel.setVisibility(INVISIBLE);
        Iv_cancel.setPadding(DensityUtil.dp2px(5), DensityUtil.dp2px(5), DensityUtil.dp2px(5), DensityUtil.dp2px(5));
        Iv_cancel.setImageResource(R.drawable.close_control);
        Iv_cancel.layout(mViewRight - DensityUtil.dp2px(55), mViewBottom - DensityUtil.dp2px(225), mViewRight - DensityUtil.dp2px(25), mViewBottom - DensityUtil.dp2px(195));
        addView(Iv_cancel, 1);

        VoiceView voiceView = new VoiceView(mContext);
        LayoutParams voiceParams = new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dp2px(10));
        voiceView.setLayoutParams(voiceParams);
        voiceView.setVisibility(INVISIBLE);
        voiceView.layout(mViewLeft, mViewBottom - DensityUtil.dp2px(40), mViewRight, mViewBottom - DensityUtil.dp2px(30));
        addView(voiceView, 2);

        ImageView mRobotView = new ImageView(mContext);
        mRobotView.setImageResource(R.drawable.robot_say_hello);
        LayoutParams IvParams = new LayoutParams(DensityUtil.dp2px(ROBOT_WIDTH), DensityUtil.dp2px(ROBOT_HEIGHT));
        mRobotView.setLayoutParams(IvParams);
        int left = (mViewWidth - DensityUtil.dp2px(ROBOT_WIDTH));
        int top = (DensityUtil.dp2px(ROBOT_HEIGHT));
        int right = (mViewWidth);
        int bottom = (DensityUtil.dp2px(ROBOT_HEIGHT * 2));
        mRobotView.layout(left, top, right, bottom);
        addView(mRobotView, 3);

        TextView mDialogTv = new TextView(mContext);
        mDialogTv.setBackground(mContext.getResources().getDrawable(R.drawable.dialog_top));
        LayoutParams TvParams = new LayoutParams(DensityUtil.dp2px(200), DensityUtil.dp2px(100));
        mDialogTv.setLayoutParams(TvParams);
        //mDialogTv.setGravity(Gravity.CENTER);
        mDialogTv.setMovementMethod(LinkMovementMethod.getInstance());
        mDialogTv.setHighlightColor(Color.TRANSPARENT);
        mDialogTv.layout(mViewWidth / 2 - DensityUtil.dp2px(100), mViewHeight - DensityUtil.dp2px(220), mViewWidth / 2 + DensityUtil.dp2px(100), mViewHeight - DensityUtil.dp2px(ROBOT_HEIGHT));
        mDialogTv.setVisibility(GONE);
        addView(mDialogTv, 4);

        TextView mControlTv = new TextView(mContext);
        mControlTv.setBackgroundColor(Color.TRANSPARENT);
        LayoutParams dialogParams = new LayoutParams(DensityUtil.dp2px(220), DensityUtil.dp2px(120));
        mControlTv.setLayoutParams(dialogParams);
        mControlTv.setGravity(Gravity.CENTER);
        mControlTv.setTextColor(Color.WHITE);
        mControlTv.setLineSpacing(0, 1.5F);
        mControlTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        mControlTv.layout(mViewWidth / 2 - DensityUtil.dp2px(120), mViewBottom - DensityUtil.dp2px(225), mViewWidth / 2 + DensityUtil.dp2px(120), mViewBottom - DensityUtil.dp2px(105));
        mControlTv.setVisibility(GONE);
        addView(mControlTv, 5);
    }

    public void setVoiceContent(String content) {
        TextView dialog = (TextView) getChildAt(5);
        Spannable textSpan = new SpannableStringBuilder(content);
        textSpan.setSpan(new AbsoluteSizeSpan(DensityUtil.sp2px(mContext, 15)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        dialog.setText(textSpan);
    }

    public void setVoiceWave(int value) {
        VoiceView wave = (VoiceView) getChildAt(2);
        wave.addData(value);
    }

    public void showDialogWindow(int tipValue) {
        if (isControlWindowShowing || isMoving) {
            return;
        }
        if (runnable != null) {
            mHandler.removeCallbacks(runnable);
        }
        isDialogWindowShow = true;
        final TextView dialogTv = (TextView) getChildAt(4);
        dialogTv.setMovementMethod(LinkMovementMethod.getInstance());
        int left = childAt.getLeft();
        int top = childAt.getTop();
        int right = childAt.getRight();
        int bottom = childAt.getBottom();
        if (left < mViewWidth / 2 - childAt.getWidth() / 2) {
            dialogTv.setBackground(mContext.getResources().getDrawable(R.drawable.dialog_left));
            dialogTv.layout(right, top, right + DensityUtil.dp2px(200), top + DensityUtil.dp2px(100));
        } else if (right > mViewWidth / 2 + childAt.getWidth() / 2) {
            dialogTv.setBackground(mContext.getResources().getDrawable(R.drawable.dialog_right));
            dialogTv.layout(left - DensityUtil.dp2px(200), top, left, top + DensityUtil.dp2px(100));
        } else {
            dialogTv.setBackground(mContext.getResources().getDrawable(R.drawable.dialog_top));
            dialogTv.layout(left - DensityUtil.dp2px(40), top - DensityUtil.dp2px(100), right + DensityUtil.dp2px(40), top);
        }
        SpannableString str = new SpannableString(mTips[tipValue]);
        switch (tipValue) {
            case 0:
                break;
            case 1:
                str.setSpan(new HyperLinkText(mContext) {
                    @Override
                    public void onClick(View widget) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("url_scheme://sh_login_activity"));
                        mContext.startActivity(intent);
                    }
                }, 11, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                str.setSpan(new HyperLinkText(mContext) {
                    @Override
                    public void onClick(View widget) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("url_scheme://sh_register_activity"));
                        mContext.startActivity(intent);
                    }
                }, 14, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 2:
                str.setSpan(new HyperLinkText(mContext) {
                    @Override
                    public void onClick(View widget) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("url_scheme://sh_house_activity"));
                        mContext.startActivity(intent);
                    }
                }, 2, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 3:
                str.setSpan(new HyperLinkText(mContext) {
                    @Override
                    public void onClick(View widget) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("url_scheme://sh_device_activity"));
                        mContext.startActivity(intent);
                    }
                }, 13, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 4:
                break;
        }
        dialogTv.setText(str);
        dialogTv.setGravity(Gravity.CENTER);
        dialogTv.setVisibility(VISIBLE);
        runnable = () -> dialogTv.setVisibility(GONE);
        mHandler.postDelayed(runnable, 2 * 1000);
    }

    private void showControlWindow() {
        if (mHandler.hasMessages(10086)) {
            mHandler.removeMessages(10086);
        }
        if (isDialogWindowShow) {
            getChildAt(4).setVisibility(GONE);
        }
        if (this.childAt.getRight() > mViewRight) {
            ObjectAnimator rotation = ObjectAnimator
                    .ofFloat(this.childAt, "rotation", -20.0F, 0.0F)
                    .setDuration(10);
            rotation.start();
        } else if (this.childAt.getLeft() < mViewLeft) {
            ObjectAnimator rotation = ObjectAnimator
                    .ofFloat(this.childAt, "rotation", 20.0F, 0.0F)
                    .setDuration(10);
            rotation.start();
        }
        childAt.layout(mViewWidth / 2 - childAt.getWidth() / 2, mViewBottom - childAt.getHeight(), mViewWidth / 2 + childAt.getWidth() / 2, mViewBottom);
        ((Activity) mContext).runOnUiThread(() -> {
            ((ImageView) RobotView.this.childAt).setImageResource(R.drawable.robot_expand);
            ((AnimationDrawable) (((ImageView) RobotView.this.childAt).getDrawable())).start();
        });
        final View childAt0 = getChildAt(0);
        final View childAt1 = getChildAt(1);
        childAt0.setVisibility(VISIBLE);
        childAt1.setVisibility(VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translationChildAt0 = ObjectAnimator
                .ofFloat(childAt0, "translationY", childAt0.getHeight(), 0.0F)//
                .setDuration(1000);
        ObjectAnimator translationChildAt1 = ObjectAnimator
                .ofFloat(childAt1, "translationY", childAt0.getHeight(), 0.0F)//
                .setDuration(1000);
        animatorSet.playTogether(translationChildAt0, translationChildAt1);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isControlWindowShowing = true;
                getChildAt(2).setVisibility(VISIBLE);
                initContent();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initContent() {
        TextView dialogContent = (TextView) getChildAt(5);
        dialogContent.setVisibility(VISIBLE);
        String text = "请说出控制指令：\n“打开报事报修、查看物业费···”\n“开灯、回家了、上班去了···”";
        Spannable textSpan = new SpannableStringBuilder(text);
        textSpan.setSpan(new AbsoluteSizeSpan(DensityUtil.sp2px(mContext, 15)), 0, text.indexOf("令") + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(DensityUtil.sp2px(mContext, 12)), text.indexOf("令") + 1, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        dialogContent.setText(textSpan);
    }

    private void dismissControlWindow() {
        VoiceView childAt2 = (VoiceView) getChildAt(2);
        childAt2.setVisibility(GONE);
        childAt2.clearData();
        getChildAt(5).setVisibility(GONE);
        final View childAt0 = getChildAt(0);
        final View childAt1 = getChildAt(1);
        storedRobot();
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translationChildAt0 = ObjectAnimator
                .ofFloat(childAt0, "translationY", 0.0F, childAt0.getHeight())//
                .setDuration(1000);
        ObjectAnimator translationChildAt1 = ObjectAnimator
                .ofFloat(childAt1, "translationY", 0.0F, childAt0.getHeight())//
                .setDuration(1000);
        animatorSet.playTogether(translationChildAt0, translationChildAt1);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                childAt0.setVisibility(INVISIBLE);
                childAt1.setVisibility(INVISIBLE);
                isControlWindowShowing = false;
                //childAt.layout(lastLeft,lastTop,lastRight,lastBottom);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void Appearance() {
        isAppearing = true;
        childAt = getChildAt(3);
        AnimatorSet animationSet = new AnimatorSet();
        ValueAnimator valueAnimatorX = ValueAnimator.ofFloat(mViewWidth, mViewWidth / 2 + DensityUtil.dp2px(50));
        valueAnimatorX.setDuration(800);
        valueAnimatorX.addUpdateListener(animation -> XAnimatedValue = (float) animation.getAnimatedValue());
        ValueAnimator valueAnimatorY = ValueAnimator.ofFloat(DensityUtil.dp2px(120), mViewHeight - childAt.getHeight(), mViewHeight - childAt.getHeight() - DensityUtil.dp2px(60), mViewHeight - childAt.getHeight());
        valueAnimatorY.setDuration(1000);
        AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator(1.5F);
        valueAnimatorY.setInterpolator(accelerateInterpolator);
        valueAnimatorY.addUpdateListener(animation -> {
            YAnimatedValue = (float) animation.getAnimatedValue();
            childAt.layout((int) (XAnimatedValue - childAt.getWidth()),
                    (int) (YAnimatedValue),
                    (int) (XAnimatedValue),
                    (int) (YAnimatedValue + childAt.getHeight()));
        });
        valueAnimatorY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                childAt.layout(mViewWidth / 2 - childAt.getWidth() / 2, mViewBottom - childAt.getHeight(), mViewWidth / 2 + childAt.getWidth() / 2, mViewBottom);
                AnimationDrawable drawable = (AnimationDrawable) ((ImageView) childAt).getDrawable();
                drawable.start();
                mHandler.postDelayed(() -> {
                    if (isAppearing) {
                        //showDialogWindow(Html.fromHtml("<font color='#222222'>我是小冶，一天也不耽误，一天也不懈怠</font>"));
                        showDialogWindow(0);
                    }
                }, 1000);
                mHandler.postDelayed(() -> {
                    if (isAppearing) {
                        tipsAfterStored();
                    }
                }, 2000);
                //mHandler.sendEmptyMessageDelayed(10086,13*1000);
                mHandler.postDelayed(() -> {
                    if (isAppearing) {
                        mHandler.sendEmptyMessage(10086);
                        isAppearing = false;
                    }
                }, 4 * 1000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animationSet.playTogether(valueAnimatorX, valueAnimatorY);
        animationSet.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveX = 0;
        moveY = 0;
        int actionMasked = event.getActionMasked();
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                lastX = rawX;
                lastY = rawY;
                childAt = getChildAt(3);
                View childAt1 = getChildAt(1);
                int left = this.childAt.getLeft();
                int top = this.childAt.getTop();
                int right = this.childAt.getRight();
                int bottom = this.childAt.getBottom();
                childRect = new Rect(left, top, right, bottom);
                dismissRect = new Rect(childAt1.getLeft(), childAt1.getTop(), childAt1.getRight(), childAt1.getBottom());
                if (childRect.contains(downX, downY)) {
                    isConsume = true;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!isControlWindowShowing && !isInMotion && !isAppearing) {
                                if (moveX == 0 && moveY == 0) {
                                    isLongClick = true;
                                    if (mHandler.hasMessages(10086)) {
                                        mHandler.removeMessages(10086);
                                    }
                                } else {
                                    if (Math.abs(moveX - downX) < 20 && Math.abs(moveY - downY) < 20) {
                                        isLongClick = true;
                                        if (mHandler.hasMessages(10086)) {
                                            mHandler.removeMessages(10086);
                                        }
                                    }
                                }
                                if (isLongClick && !isInMotion) {
                                    Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                                    if (vibrator != null) {
                                        vibrator.vibrate(50);
                                    }
                                    ((Activity) mContext).runOnUiThread(() -> {
                                        ((ImageView) RobotView.this.childAt).setImageResource(R.drawable.robot_struggle);
                                        ((AnimationDrawable) (((ImageView) RobotView.this.childAt).getDrawable())).start();
                                    });
                                }
                            }
                        }
                    }, 600);
                } else {
                    isConsume = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getX();
                moveY = (int) event.getY();
                int offsetX = rawX - lastX;
                int offsetY = rawY - lastY;
                Rect childRectAfterMove = new Rect(this.childAt.getLeft() + offsetX,
                        this.childAt.getTop() + offsetY,
                        this.childAt.getRight() + offsetX,
                        this.childAt.getBottom() + offsetY);
                if (isConsume && isLongClick) {
                    if (isDialogWindowShow) {
                        getChildAt(4).setVisibility(GONE);
                    }
                    isMoving = true;
                    if (mFrameRect.contains(childRectAfterMove)) {
                        this.childAt.layout(this.childAt.getLeft() + offsetX,
                                this.childAt.getTop() + offsetY,
                                this.childAt.getRight() + offsetX,
                                this.childAt.getBottom() + offsetY);
                    } else {
                        //stored in right
                        if (this.childAt.getRight() > mViewRight) {
                            ObjectAnimator rotation = ObjectAnimator
                                    .ofFloat(this.childAt, "rotation", -20.0F, 0.0F)
                                    .setDuration(10);
                            rotation.start();
                            this.childAt.layout(mViewRight - this.childAt.getWidth(),
                                    this.childAt.getTop(),
                                    mViewRight,
                                    this.childAt.getBottom());
                        } else if (this.childAt.getLeft() < mViewLeft) {
                            ObjectAnimator rotation = ObjectAnimator
                                    .ofFloat(this.childAt, "rotation", 20.0F, 0.0F)
                                    .setDuration(10);
                            rotation.start();
                            this.childAt.layout(0,
                                    this.childAt.getTop(),
                                    mViewLeft + this.childAt.getWidth(),
                                    this.childAt.getBottom());
                        }
                    }
                }
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                isMoving = false;
                if (isLongClick && !isInMotion) {
                    if (this.childAt.getRight() > mViewRight || this.childAt.getLeft() < mViewLeft) {
                        ((ImageView) this.childAt).setImageResource(R.drawable.robot_shrink);
                        ((AnimationDrawable) (((ImageView) RobotView.this.childAt).getDrawable())).start();
                    } else {
                        ((ImageView) this.childAt).setImageResource(R.drawable.robot_shrink);
                        if (mHandler != null) {
                            if (mHandler.hasMessages(10086)) {
                                mHandler.removeMessages(10086);
                            }
                            mHandler.sendEmptyMessageDelayed(10086, 2 * 1000);
                        }
                    }
                }
                if (isConsume && !isLongClick && Math.abs(upX - downX) < 20 && Math.abs(upY - downY) < 20) {
                    if (childRect.contains(downX, downY)) {
                        if (!isFastClick()) {
                            if (isControlWindowShowing) {
                                if (onSpeakListener != null) {
                                    onSpeakListener.onStartSpeak();
                                }
                            } else {
                                if (!isInMotion) {
                                    boolean hasRoom = SharedPreferencesUtils.getInstance().getBooleanParam("hasRoom");
                                    boolean isLogin = SharedPreferencesUtils.getInstance().getBooleanParam("isLogin");
                                    if (!isLogin && !isAppearing) {
                                        if (this.childAt.getRight() > mViewRight) {
                                            ObjectAnimator rotation = ObjectAnimator
                                                    .ofFloat(this.childAt, "rotation", -20.0F, 0.0F)
                                                    .setDuration(10);
                                            rotation.start();
                                        } else if (this.childAt.getLeft() < mViewLeft) {
                                            ObjectAnimator rotation = ObjectAnimator
                                                    .ofFloat(this.childAt, "rotation", 20.0F, 0.0F)
                                                    .setDuration(10);
                                            rotation.start();
                                        }
                                        if (mStoredRunnable != null) {
                                            mHandler.removeCallbacks(mStoredRunnable);
                                        }
                                        if (mHandler.hasMessages(10086)) {
                                            mHandler.removeMessages(10086);
                                        }
                                        childAt.layout(mViewWidth / 2 - childAt.getWidth() / 2, mViewBottom - childAt.getHeight(), mViewWidth / 2 + childAt.getWidth() / 2, mViewBottom);
                                        ((Activity) mContext).runOnUiThread(() -> {
                                            ((ImageView) RobotView.this.childAt).setImageResource(R.drawable.robot_expand);
                                            ((AnimationDrawable) (((ImageView) RobotView.this.childAt).getDrawable())).start();
                                        });
                                        //showDialogWindow(Html.fromHtml("<font color='#222222'>你好像还没有登录，赶快</font>" + "<font color='#4690EF'><a href=\"url_scheme://sh_login_activity\">登录</a></font>" + "<font color='#222222'>或者</font>" + "<font color='#4690EF'><a href=\"url_scheme://sh_register_activity\">注册</a></font>" + "<font color='#222222'>吧</font>"));
                                        showDialogWindow(1);
                                        mStoredRunnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                storedRobot();
                                            }
                                        };
                                        mHandler.postDelayed(mStoredRunnable, 2000);
                                        isAppearing = false;
                                    } else if (!hasRoom && !isAppearing) {
                                        if (this.childAt.getRight() > mViewRight) {
                                            ObjectAnimator rotation = ObjectAnimator
                                                    .ofFloat(this.childAt, "rotation", -20.0F, 0.0F)
                                                    .setDuration(10);
                                            rotation.start();
                                        } else if (this.childAt.getLeft() < mViewLeft) {
                                            ObjectAnimator rotation = ObjectAnimator
                                                    .ofFloat(this.childAt, "rotation", 20.0F, 0.0F)
                                                    .setDuration(10);
                                            rotation.start();
                                        }
                                        if (mStoredRunnable != null) {
                                            mHandler.removeCallbacks(mStoredRunnable);
                                        }
                                        if (mHandler.hasMessages(10086)) {
                                            mHandler.removeMessages(10086);
                                        }
                                        childAt.layout(mViewWidth / 2 - childAt.getWidth() / 2, mViewBottom - childAt.getHeight(), mViewWidth / 2 + childAt.getWidth() / 2, mViewBottom);
                                        ((Activity) mContext).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((ImageView) RobotView.this.childAt).setImageResource(R.drawable.robot_expand);
                                                ((AnimationDrawable) (((ImageView) RobotView.this.childAt).getDrawable())).start();
                                            }
                                        });
                                        //showDialogWindow(Html.fromHtml("赶快" + "<font color='#4690EF'><a href=\"url_scheme://sh_house_activity\">绑定房屋</a></font>" + "吧"));
                                        showDialogWindow(2);
                                        mStoredRunnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                storedRobot();
                                            }
                                        };
                                        mHandler.postDelayed(mStoredRunnable, 3000);
                                        isAppearing = false;
                                    } else if (isLogin && hasRoom) {
                                        if (CheckPermissionUtils.getInstance().checkSelfPermission(Manifest.permission.RECORD_AUDIO)) {
                                            showControlWindow();
                                            isAppearing = false;
                                            if (onSpeakListener != null) {
                                                onSpeakListener.onStartSpeak();
                                            }
                                        } else {
                                            CheckPermissionUtils.getInstance().initPermission(mContext, 110, new PermissionCallBack() {
                                                @Override
                                                public void permissionNext() {
                                                    showControlWindow();
                                                    isAppearing = false;
                                                    if (onSpeakListener != null) {
                                                        onSpeakListener.onStartSpeak();
                                                    }
                                                }
                                            }, Manifest.permission.RECORD_AUDIO);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (dismissRect.contains(downX, downY)) {
                        if (!isFastClick()) {
                            dismissControlWindow();
                            if (onSpeakListener != null) {
                                onSpeakListener.onStopSpeak();
                            }
                        }
                    }
                }
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                isLongClick = false;
                break;
        }
        isConsume = isConsume || isControlWindowShowing;
        return isConsume;
    }

    public boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        int MIN_DELAY_TIME = 1000;
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
            lastClickTime = currentClickTime;
        }
        return flag;
    }

    public void storedRobot() {
        if (this.childAt.getRight() > mViewRight || this.childAt.getLeft() < mViewLeft) {
            return;
        }
        if (isDialogWindowShow) {
            getChildAt(4).setVisibility(GONE);
        }
        isInMotion = true;
        ((ImageView) childAt).setImageResource(R.drawable.robot_shrink);
        ((AnimationDrawable) (((ImageView) childAt).getDrawable())).start();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            int left = childAt.getLeft();
            if (left < mViewWidth / 2 - childAt.getWidth() / 2) {
                AnimatorSet animSet = new AnimatorSet();
                ObjectAnimator rotate = ObjectAnimator
                        .ofFloat(childAt, "rotation", 0.0F, -330.0F)
                        .setDuration(1000);
                ValueAnimator valueAnim = ValueAnimator.ofFloat(childAt.getRight(), DensityUtil.dp2px(60));
                valueAnim.setDuration(1000);
                valueAnim.addUpdateListener(animation -> {
                    float animatedValue = (float) animation.getAnimatedValue();
                    childAt.layout((int) (animatedValue - DensityUtil.dp2px(120)),
                            childAt.getTop(),
                            (int) (animatedValue),
                            childAt.getBottom());
                });
                animSet.playTogether(rotate, valueAnim);
                animSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isInMotion = false;
                        tipsAfterStored();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animSet.start();
            } else {
                AnimatorSet animationSet = new AnimatorSet();
                ObjectAnimator rotation = ObjectAnimator
                        .ofFloat(childAt, "rotation", 0.0F, 330.0F)
                        .setDuration(1000);
                ValueAnimator valueAnimatorX = ValueAnimator.ofFloat(childAt.getLeft(), mViewWidth - childAt.getWidth() / 2);
                valueAnimatorX.setDuration(1000);
                valueAnimatorX.addUpdateListener(animation -> {
                    float animatedValue = (float) animation.getAnimatedValue();
                    childAt.layout((int) (animatedValue),
                            childAt.getTop(),
                            (int) (animatedValue + childAt.getWidth()),
                            childAt.getBottom());
                });
                animationSet.playTogether(rotation, valueAnimatorX);
                animationSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isInMotion = false;
                        tipsAfterStored();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animationSet.start();
            }
        }, 1040);
    }

    private void tipsAfterStored() {
        boolean hasRoom = SharedPreferencesUtils.getInstance().getBooleanParam("hasRoom");
        boolean hasDevice = SharedPreferencesUtils.getInstance().getBooleanParam("hasDevice");
        if (!SharedPreferencesUtils.getInstance().getBooleanParam("isLogin")) {
            //showDialogWindow(Html.fromHtml("<font color='#222222'>你好像还没有登录，赶快</font>" + "<font color='#4690EF'><a href=\"url_scheme://sh_login_activity\">登录</a></font>" + "<font color='#222222'>或者</font>" + "<font color='#4690EF'><a href=\"url_scheme://sh_register_activity\">注册</a></font>" + "<font color='#222222'>吧</font>"));
            showDialogWindow(1);
            return;
        }
        if (!hasRoom) {
            showDialogWindow(2);
            //showDialogWindow(Html.fromHtml("<font color='#222222'>赶快</font>" + "<font color='#4690EF'><a href=\"url_scheme://sh_house_activity\">绑定房屋</a></font>" + "吧"));
            return;
        }
        if (!hasDevice) {
            showDialogWindow(3);
            //showDialogWindow(Html.fromHtml("<font color='#222222'>您家还没有智能家居呢，赶快</font>" + "<font color='#4690EF'><a href=\"url_scheme://sh_device_activity\">添加设备</a></font>" + "吧"));
            return;
        }
        showDialogWindow(4);
        //showDialogWindow(Html.fromHtml("<font color='#222222'>点我的头可以进行语音操作哦</font>"));
    }

    public interface OnSpeakListener {
        void onStartSpeak();

        void onStopSpeak();
    }

    public void setOnSpeakListener(OnSpeakListener onSpeakListener) {
        this.onSpeakListener = onSpeakListener;
    }
}
