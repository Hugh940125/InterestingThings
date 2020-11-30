package com.example.hugh.interesting.refresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hugh.interesting.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefreshActivity extends AppCompatActivity {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        ButterKnife.bind(this);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onNormal() {

            }

            @Override
            public void onLoose() {

            }

            @Override
            public void onRefresh() {
                ExecutorService executorService = Executors.newCachedThreadPool();
                Future<String> call = executorService.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        //System.out.println("call");
                        TimeUnit.SECONDS.sleep(1);
                        return "str";
                    }
                });
                try {
                    System.out.println(call.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executorService.shutdown();
                refresh.setRefreshing(false);
            }
        });
    }
}
