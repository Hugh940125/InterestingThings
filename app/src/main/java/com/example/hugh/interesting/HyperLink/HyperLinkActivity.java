package com.example.hugh.interesting.HyperLink;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugh.interesting.R;
import com.example.hugh.interesting.Utils.DensityUtil;

public class HyperLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyper_link);

        TextView hyperLink = findViewById(R.id.hyperLink);
        TextView spanString = findViewById(R.id.span_string);
        hyperLink.setLineSpacing(0,1.5f);
        hyperLink.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        hyperLink.setGravity(Gravity.CENTER);
        SpannableString str = new SpannableString("超文本：登录 超文本：注册");
        str.setSpan(new HyperLinkText(this) {
            @Override
            public void onClick(View widget) {
                Toast.makeText(HyperLinkActivity.this, "登录", Toast.LENGTH_SHORT).show();
            }
        },4,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new HyperLinkText(this) {
            @Override
            public void onClick(View widget) {
                Toast.makeText(HyperLinkActivity.this, "注册", Toast.LENGTH_SHORT).show();
            }
        },11,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //当然这里也可以通过setSpan来设置哪些位置的文本哪些颜色
        hyperLink.setText(str);
        hyperLink.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        hyperLink.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明

        SpannableString spannableString = new SpannableString("颜色大小正常粗体斜体粗斜体下划线删除线");
        //字体颜色
        spannableString.setSpan(new ForegroundColorSpan(Color.RED),0,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //字体大小
        spannableString.setSpan(new AbsoluteSizeSpan(DensityUtil.sp2px(this,16)),2,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体样式: NORMAL正常，BOLD粗体，ITALIC斜体，BOLD_ITALIC粗斜体
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 10, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置下划线
        spannableString.setSpan(new UnderlineSpan(), 13, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置删除线
        spannableString.setSpan(new StrikethroughSpan(), 16, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setText(spannableString);
    }
}
