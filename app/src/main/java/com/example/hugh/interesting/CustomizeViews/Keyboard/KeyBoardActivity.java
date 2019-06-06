package com.example.hugh.interesting.CustomizeViews.Keyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hugh.interesting.R;

public class KeyBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_board);

        KeyBoard keyboard = findViewById(R.id.keyboard);
        PwdEditText edit_pwd = findViewById(R.id.edit_pwd);

        keyboard.addOnTextUpdateListener(edit_pwd::setText);
    }
}
