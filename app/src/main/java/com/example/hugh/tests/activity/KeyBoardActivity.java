package com.example.hugh.tests.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hugh.tests.R;
import com.example.hugh.tests.views.KeyBoard;
import com.example.hugh.tests.views.PwdEditText;

public class KeyBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_board);

        KeyBoard keyboard = findViewById(R.id.keyboard);
        final PwdEditText edit_pwd = findViewById(R.id.edit_pwd);

        keyboard.addOnTextUpdateListener(new KeyBoard.OnTextUpdateListener() {
            @Override
            public void OnTextUpdate(String text) {
                edit_pwd.setText(text);
            }
        });
    }
}
