package com.example.hugh.interesting.sp_util;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveListValue();
        saveMapValue();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getListValue();
        getMapValue();
    }

    public void saveListValue(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("hello");
        strings.add("world");
        SP_Utils.putStrListValue(this,"list",strings);
    }

    public void getListValue(){
        List<String> list = SP_Utils.getStrListValue(this, "list");
        //String s = list.toString();
        Log.e("打印list[0]",list.get(0));
        Log.e("打印list[1]",list.get(1));
    }

    public void saveMapValue(){
        HashMap<String, String> map = new HashMap<>();
        map.put("key1","hello");
        map.put("key2","world");
        SP_Utils.putMapValue(this,"map",map);
    }

    public void getMapValue(){
        HashMap map = SP_Utils.getMapValue(this, "map");
        Log.e("打印map",map.toString());
    }
}
