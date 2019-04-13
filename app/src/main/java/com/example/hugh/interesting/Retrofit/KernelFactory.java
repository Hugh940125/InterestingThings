package com.example.hugh.interesting.Retrofit;


public class KernelFactory {
    private static KernelService mKernelService = null;
    private static final Object WATCH = new Object();

    public static KernelService getKernelApi(){
        synchronized (WATCH) {
            if (mKernelService == null) {
                mKernelService = new KernelClient().getService();
            }
        }
        return mKernelService;
    }
}
