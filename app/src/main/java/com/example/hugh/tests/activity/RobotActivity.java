package com.example.hugh.tests.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hugh.tests.R;

public class RobotActivity extends AppCompatActivity {

    private ImageView iv;
    private Button bt_1;
    private TextView tv_dialog;
    private Button bt_2;
    private int widthPixels;
    private int heightPixels;
    private ImageView iv_right;
    private LinearLayout root;
    private RelativeLayout rl_content;
    private ImageView iv_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);

        iv = findViewById(R.id.iv);
        iv_right = findViewById(R.id.iv_right);
        bt_1 = findViewById(R.id.bt_1);
        bt_2 = findViewById(R.id.bt_2);
        tv_dialog = findViewById(R.id.tv_dialog);
        root = findViewById(R.id.root);
        rl_content = findViewById(R.id.rl_content);
        rl_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv_cancel = findViewById(R.id.iv_cancel);

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (windowManager != null) {
            Display defaultDisplay = windowManager.getDefaultDisplay();
            final DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);
            widthPixels = displayMetrics.widthPixels;
            heightPixels = displayMetrics.heightPixels;
        }

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator translationY = ObjectAnimator
                        .ofFloat(rl_content, "translationY", 0.0F, rl_content.getHeight())//
                        .setDuration(1000);

                final AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator rotation = ObjectAnimator
                        .ofFloat(iv, "rotation",  0.0F,340.0F)//
                        .setDuration(1000);

                ObjectAnimator rotation1 = ObjectAnimator
                        .ofFloat(iv, "translationX", 0.0F, widthPixels /2)//
                        .setDuration(1000);
                animatorSet.playTogether(rotation,rotation1);

                int width = tv_dialog.getWidth() / 2;
                ObjectAnimator rotation2 = ObjectAnimator
                        .ofFloat(tv_dialog, "translationX",  0.0F,widthPixels /2 - width)//
                        .setDuration(1000);
                animatorSet.playTogether(rotation,rotation1,rotation2,translationY);
                animatorSet.addListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        rl_content.setVisibility(View.INVISIBLE);
                        iv.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                ObjectAnimator rotation3 = ObjectAnimator
                                        .ofFloat(rl_content, "translationY", rl_content.getHeight(),0.0F)//
                                        .setDuration(1000);

                                final AnimatorSet animatorSet = new AnimatorSet();
                                ObjectAnimator rotation = ObjectAnimator
                                        .ofFloat(iv, "rotation", 340.0F, 0.0F)//
                                        .setDuration(1000);

                                ObjectAnimator rotation1 = ObjectAnimator
                                        .ofFloat(iv, "translationX", widthPixels /2, 0.0F )//
                                        .setDuration(1000);
                                animatorSet.playTogether(rotation,rotation1);

                                int width = tv_dialog.getWidth() / 2;
                                ObjectAnimator rotation2 = ObjectAnimator
                                        .ofFloat(tv_dialog, "translationX", widthPixels /2 - width, 0.0F)//
                                        .setDuration(1000);
                                animatorSet.playTogether(rotation,rotation1,rotation2,rotation3);
                                animatorSet.addListener(new Animator.AnimatorListener() {

                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        rl_content.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        iv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Rotate360DegreesInPlace();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });
                                animatorSet.start();
                            }
                        });
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorSet.start();
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tv_dialog.setVisibility(View.INVISIBLE);
                ObjectAnimator rotation3 = ObjectAnimator
                        .ofFloat(rl_content, "translationY", rl_content.getHeight(),0.0F)//
                        .setDuration(1000);

                final AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator rotation = ObjectAnimator
                        .ofFloat(iv, "rotation", 340.0F, 0.0F)//
                        .setDuration(1000);

                ObjectAnimator rotation1 = ObjectAnimator
                        .ofFloat(iv, "translationX", widthPixels /2, 0.0F )//
                        .setDuration(1000);
                animatorSet.playTogether(rotation,rotation1);

                int width = tv_dialog.getWidth() / 2;
                ObjectAnimator rotation2 = ObjectAnimator
                        .ofFloat(tv_dialog, "translationX", widthPixels /2 - width, 0.0F)//
                        .setDuration(1000);
                animatorSet.playTogether(rotation,rotation1,rotation2,rotation3);
                animatorSet.addListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        rl_content.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Rotate360DegreesInPlace();
                            }
                        });
                        tv_dialog.setVisibility(View.VISIBLE);
                        tv_dialog.setText(Html.fromHtml("你敢"+"<a href=\"url_scheme://house_plan_activity\">点我试试</a>"+"吗？"));//
                        tv_dialog.setMovementMethod(LinkMovementMethod.getInstance());
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorSet.start();
            }
        });

        bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sideRetracted();
            }
        });

        bt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Appearance();
            }
        });
    }

    private void Appearance() {
        iv_right.setVisibility(View.VISIBLE);
        iv.setVisibility(View.GONE);
        AnimatorSet animatorSet = new AnimatorSet();
        Interpolator accelerateInterpolator = new AccelerateInterpolator();
        final ObjectAnimator rotation = ObjectAnimator
                .ofFloat(iv_right, "translationX", 0.0F, -widthPixels/2 + iv_right.getWidth()/2)
                .setDuration(500);
        final ObjectAnimator rotation1 = ObjectAnimator
                .ofFloat(iv_right, "translationY", 0.0F, heightPixels/2 - iv_right.getHeight()*2)
                .setDuration(500);
        rotation1.setInterpolator(accelerateInterpolator);
        animatorSet.playTogether(rotation,rotation1);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
                ObjectAnimator translationY = ObjectAnimator
                        .ofFloat(iv_right, "translationY", heightPixels / 2 - iv_right.getHeight() * 2, heightPixels / 2 - iv_right.getHeight() * 3, heightPixels / 2 - iv_right.getHeight() * 2)//
                        .setDuration(500);
                translationY.setInterpolator(decelerateInterpolator);
                translationY.start();
                translationY.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        iv_right.setVisibility(View.GONE);
                        iv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private void sideRetracted() {
        final AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator rotation = ObjectAnimator
                .ofFloat(iv, "rotation", 0.0F, 340.0F)//
                .setDuration(1000);

        ObjectAnimator rotation1 = ObjectAnimator
                .ofFloat(iv, "translationX", 0.0F, widthPixels /2)//
                .setDuration(1000);
        animatorSet.playTogether(rotation,rotation1);

        int width = tv_dialog.getWidth() / 2;
        ObjectAnimator rotation2 = ObjectAnimator
                .ofFloat(tv_dialog, "translationX", 0.0F, widthPixels /2 - width)//
                .setDuration(1000);
        animatorSet.playTogether(rotation,rotation1,rotation2);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                tv_dialog.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tv_dialog.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private void Rotate360DegreesInPlace() {
        ObjectAnimator.ofFloat(iv, "rotation",  0.0F,360.0F)//
                .setDuration(1000)
                .start();
    }

}
