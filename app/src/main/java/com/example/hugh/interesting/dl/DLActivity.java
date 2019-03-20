package com.example.hugh.interesting.dl;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.hugh.interesting.R;
import com.example.hugh.interesting.Base.BaseActivity;

import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;

public class DLActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dl);
    }

    @Override
    public int initLayout() {
        return 0;
    }

    @Override
    public void initView() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                createAndUseNetwork();
            }
        });
    }

    private void createAndUseNetwork() {
        DenseLayer inputLayer = new DenseLayer.Builder()
                .nIn(2)
                .nOut(3)
                .name("Input")
                .build();

        DenseLayer hiddenLayer = new DenseLayer.Builder()
                .nIn(3)
                .nOut(2)
                .name("Hidden")
                .build();

        OutputLayer outputLayer = new OutputLayer.Builder()
                .nIn(2)
                .nOut(1)
                .name("Output")
                .build();
    }
}
