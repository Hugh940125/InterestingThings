package com.example.hugh.interesting.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.example.hugh.interesting.R;
import com.example.hugh.interesting.RecyclerView.refresh_layout.RefreshListenerAdapter;
import com.example.hugh.interesting.RecyclerView.refresh_layout.RefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewTestActivity extends AppCompatActivity {

    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    @BindView(R.id.rl_test)
    RefreshLayout rlTest;
    private ArrayList<String> strs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_test);
        ButterKnife.bind(this);
        SnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(rvTest);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTest.setLayoutManager(linearLayoutManager);
        TestAdapter testAdapter = new TestAdapter(this);
        rvTest.setAdapter(testAdapter);
        strs = new ArrayList<>();
        strs.add("001");
        strs.add("002");
        strs.add("003");
        strs.add("004");
        strs.add("005");
        strs.add("006");
        strs.add("007");
        strs.add("007");
        strs.add("008");
        strs.add("0010");
        testAdapter.addRefreshData(strs);
        rlTest.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                new Handler().postDelayed(() -> {
                    rlTest.finishRefreshing();
                },2000);
            }

            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                new Handler().postDelayed(()->{
                    strs.add("0011");
                    strs.add("0012");
                    strs.add("0013");
                    strs.add("0014");
                    strs.add("0015");
                    strs.add("0016");
                    strs.add("0017");
                    strs.add("0017");
                    strs.add("0018");
                    strs.add("0020");
                    testAdapter.addLoadData(strs);
                    rlTest.finishLoadmore();
                },2000);
            }
        });
    }
}
