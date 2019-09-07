package com.example.hugh.interesting.Activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.hugh.interesting.R;

public class SVGActivity extends AppCompatActivity {

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private CheckBox image4;
    private ImageView image5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);

        image1 = findViewById(R.id.image1);
        image1.setOnClickListener(view -> animate1());

        image2 = findViewById(R.id.image2);
        image2.setOnClickListener(view -> animate2());

        image3 = findViewById(R.id.image3);
        image3.setOnClickListener(view -> animate3());

        image4 = findViewById(R.id.image4);
        image4.setOnCheckedChangeListener((buttonView, isChecked) -> animate4());

        image5 = findViewById(R.id.image5);
        image5.setOnClickListener(v -> animate5());
    }

    public void animate1() {
        Drawable drawable = image1.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    public void animate2() {
        Drawable drawable = image2.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    public void animate3() {
        Drawable drawable = image3.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    public void animate4() {
        Drawable drawable = image4.getBackground();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    public void animate5() {
        Drawable drawable = image5.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}
