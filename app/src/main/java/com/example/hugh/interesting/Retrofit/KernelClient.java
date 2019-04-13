package com.example.hugh.interesting.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class KernelClient {
    private KernelService mService;
    public KernelClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Links.BASE_URL)
                .client(OkHttpManager.getInstance())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(KernelService.class);
    }

    public KernelService getService() {
        return mService;
    }
}
