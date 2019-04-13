package com.example.hugh.interesting.RxJava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.hugh.interesting.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ObservableTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable_test);
        //第三步 确定订阅关系
        mObservable.observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在io线程(子线程)
                .subscribe(mObserver);

        mFlowable.subscribe(mSubscriber);
    }

    /**
     * 1、 Observable 和 Observer 的方式，此种方式不支持背压，事件量不超过1000时推荐使用 ----------------------------------------------
     */
    //第一步 创建Observable
    Observable<Integer> mObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> e) throws Exception {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
            e.onNext(4);
            e.onComplete();
        }
    });

    //第一步 创建Observer
    Observer<Integer> mObserver = new Observer<Integer>() {

        //这是新加入的方法，在订阅后发送数据之前，
        //回首先调用这个方法，而Disposable可用于取消订阅
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(Integer integer) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
    /**
     * ---------------------------------------------分 割 线-----------------------------------------------------
     *
     * 2、Flowable 和 Subscriber 的方式，此方式支持背压，适合事件量较大时使用
     */
    Flowable<Integer> mFlowable = Flowable.range(0,10);

    Subscriber<Integer> mSubscriber = new Subscriber<Integer>() {
        Subscription sub;
        //当订阅后，会首先调用这个方法，其实就相当于onStart()，
        //传入的Subscription s参数可以用于请求数据或者取消订阅
        @Override
        public void onSubscribe(Subscription s) {
            Log.w("TAG","onsubscribe start");
            sub = s;
            sub.request(1);
            Log.w("TAG","onsubscribe end");
        }

        @Override
        public void onNext(Integer o) {
            Log.w("TAG","onNext--->"+o);
            sub.request(1);
        }
        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
        }
        @Override
        public void onComplete() {
            Log.w("TAG","onComplete");
        }
    };
}
