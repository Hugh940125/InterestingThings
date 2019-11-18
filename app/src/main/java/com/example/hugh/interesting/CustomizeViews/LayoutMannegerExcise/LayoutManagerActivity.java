package com.example.hugh.interesting.CustomizeViews.LayoutMannegerExcise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hugh.interesting.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LayoutManagerActivity extends AppCompatActivity {

    @BindView(R.id.rv_layout_manager)
    RecyclerView rvLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_manager);
        ButterKnife.bind(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLayoutManager.setLayoutManager(manager);
        WorkOrderListAdapter workOrderListAdapter = new WorkOrderListAdapter(this);
        rvLayoutManager.setAdapter(workOrderListAdapter);

        ArrayList<String> contents = new ArrayList<>();
        contents.add("布局管理器测试-1");
        contents.add("布局管理器测试-2");
        contents.add("布局管理器测试-3");
        contents.add("布局管理器测试-4");
        contents.add("布局管理器测试-5");
        contents.add("布局管理器测试-6");
        workOrderListAdapter.addRefreshData(contents);
    }
}
