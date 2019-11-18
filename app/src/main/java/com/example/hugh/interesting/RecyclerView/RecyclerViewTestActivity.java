package com.example.hugh.interesting.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.hugh.interesting.R;
import com.example.hugh.interesting.RecyclerView.refresh_layout.RefreshLayout;
import com.example.hugh.interesting.RecyclerView.refresh_layout.RefreshListenerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewTestActivity extends AppCompatActivity {

    @BindView(R.id.rv_test)
    RecyclerCoverFlow rvTest;
    @BindView(R.id.rl_test)
    RefreshLayout rlTest;
    @BindView(R.id.bt_next)
    Button btNext;
    @BindView(R.id.bt_last)
    Button btLast;
    private ArrayList<Integer> strs;
    private int mPosition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_test);
        ButterKnife.bind(this);
//        SnapHelper snapHelper = new StartSnapHelper();
//        snapHelper.attachToRecyclerView(rvTest);
        CoverFlowLayoutManger linearLayoutManager = new CoverFlowLayoutManger(false, false, false, 0.5F);
        rvTest.setLayoutManager(linearLayoutManager);
        TestAdapter testAdapter = new TestAdapter(this);
        rvTest.setOnItemSelectedListener(position -> mPosition = position);

        btNext.setOnClickListener(v -> {
            if (mPosition == testAdapter.getItemCount()-1) {
                return;
            }
            rvTest.smoothScrollToPosition(mPosition + 1);
        });
        btLast.setOnClickListener(v -> {
            if (mPosition == 0) {
                return;
            }
            rvTest.smoothScrollToPosition(mPosition - 1);
        });
        rvTest.setAdapter(testAdapter);
        strs = new ArrayList<>();
        strs.add(Color.RED);
        strs.add(Color.GRAY);
        strs.add(Color.GREEN);
        testAdapter.addRefreshData(strs);
        rlTest.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                new Handler().postDelayed(() -> {
                    rlTest.finishRefreshing();
                }, 2000);
            }

            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                new Handler().postDelayed(() -> {
                    strs.add(Color.RED);
                    strs.add(Color.GRAY);
                    strs.add(Color.GREEN);
                    strs.add(Color.BLUE);
                    strs.add(Color.BLACK);
                    strs.add(Color.YELLOW);
                    strs.add(Color.RED);
                    strs.add(Color.GRAY);
                    strs.add(Color.GREEN);
                    strs.add(Color.BLUE);
                    strs.add(Color.BLACK);
                    strs.add(Color.YELLOW);
                    testAdapter.addLoadData(strs);
                    rlTest.finishLoadmore();
                }, 2000);
            }
        });
    }
}
