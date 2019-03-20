package com.example.hugh.interesting.Keyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hugh.interesting.R;

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
