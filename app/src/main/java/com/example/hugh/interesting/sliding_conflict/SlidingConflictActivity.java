package com.example.hugh.interesting.sliding_conflict;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hugh.interesting.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlidingConflictActivity extends AppCompatActivity {

    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.rv_in)
    RecyclerView rvIn;
    @BindView(R.id.sv_out)
    ScrollView svOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_conflict);
        ButterKnife.bind(this);
    }
}
