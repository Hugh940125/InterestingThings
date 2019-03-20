package com.example.hugh.interesting.ViewBinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugh.interesting.R;
import com.hugh.annotator.CompileBindView;
import com.hugh.annotator.CompileOnClick;
import com.hugh.view_bind_api.CompileButterKnife;

public class ViewBinderTestActivity extends AppCompatActivity {

    @RunTimeViewIdBinder(R.id.tv_test)
    TextView tvTest;
    @CompileBindView(R.id.tv_test1)
    TextView tvTest1;
//    @CompileBindView(R.id.bt_test)
//    Button btTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_binder_test);
        RunTimeViewBinder.bind(this);
        CompileButterKnife.bind(this);
        tvTest.setText("运行时找到了");
        tvTest1.setText("编译时找到了");
    }

    @CompileOnClick(R.id.bt_test)
    public void OnClick(View view){
        Toast.makeText(this, "编译时被找到", Toast.LENGTH_SHORT).show();
    }
}
