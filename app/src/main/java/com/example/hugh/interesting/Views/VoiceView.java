package com.example.hugh.interesting.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Hugh on 2018/12/17.
 *
 */

public class VoiceView extends View {

    private Paint paint;
    private ArrayList<Integer> items;

    public VoiceView(Context context) {
        super(context);
    }

    public VoiceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        items = new ArrayList<>();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //if (System.currentTimeMillis()%1000==0){
                    items.add(4);//(int) (Math.random()*30)
                    //items.addAll(items1);
                    invalidate();
                /*}else {
                    items1.add((4));
                    items.addAll(items1);
                    invalidate();
                }*/
            }
        };
        timer.schedule(timerTask,50,100);
    }

    public void addData(List list){
        items.addAll(list);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawHistogram(canvas);
    }

    private void drawHistogram(final Canvas canvas) {
        int width = getWidth();
        final int count = width / 15;

        for (int i=0;i<count;i++){
            Rect rect = new Rect(15 * i+2, getHeight() / 2 - 2, 15 * (i+1), getHeight() / 2 + 2);
            canvas.drawRect(rect,paint);
        }

        synchronized (this){
            if (items.size()>0 && items.size()<=count){
                for (int i=0;i<items.size();i++){
                    if (items.get(i) != null){
                        Rect rect = new Rect(15 * i+2, getHeight() / 2 - items.get(i)/2, 15 * (i+1), getHeight() / 2 + items.get(i)/2);
                        canvas.drawRect(rect,paint);
                    }
                }
            }else if (items.size()>count){
                for (int i=count;i>0;i--){
                    if (items.get(items.size()-i) != null){
                        Rect rect = new Rect(15 * i+2, getHeight() / 2 - items.get(items.size()-i)/2, 15 * (i+1), getHeight() / 2 + items.get(items.size()-i)/2);
                        canvas.drawRect(rect,paint);
                    }
                }
            }
        }
    }
}
