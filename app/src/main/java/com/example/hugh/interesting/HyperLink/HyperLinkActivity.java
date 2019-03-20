package com.example.hugh.interesting.HyperLink;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
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
        hyperLink.setLineSpacing(0,1.5f);
        hyperLink.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        hyperLink.setGravity(Gravity.CENTER);
        SpannableString str = new SpannableString("超文本：登录 超文本：注册");
        str.setSpan(new HyperLinkText(this) {
            @Override
            public void onClick(View widget) {
                Toast.makeText(HyperLinkActivity.this, "第一个", Toast.LENGTH_SHORT).show();
            }
        },4,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new HyperLinkText(this) {
            @Override
            public void onClick(View widget) {
                Toast.makeText(HyperLinkActivity.this, "第二个", Toast.LENGTH_SHORT).show();
            }
        },11,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //当然这里也可以通过setSpan来设置哪些位置的文本哪些颜色
        hyperLink.setText(str);
        hyperLink.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        hyperLink.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
    }
}
