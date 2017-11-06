package com.example.hugh.tests;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SVGActivity extends AppCompatActivity {

    private ImageView image;
    private ImageView image1;
    private ImageView image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);

        image1 = (ImageView) findViewById(R.id.image1);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate1();
            }
        });

        image2 = (ImageView) findViewById(R.id.image2);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate2();
            }
        });

    }

    public void animate1(){
        Drawable drawable = image1.getDrawable();
        if (drawable instanceof Animatable){
            ((Animatable)drawable).start();
        }
    }

    public void animate2(){
        Drawable drawable = image2.getDrawable();
        if (drawable instanceof Animatable){
            ((Animatable)drawable).start();
        }
    }
}
