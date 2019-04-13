package com.example.hugh.interesting.Retrofit;

import android.util.Log;

import com.example.hugh.interesting.Utils.SharedPreferencesUtils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


public class OkHttpManager {

    private static OkHttpClient mOkHttpClient;

    public static OkHttpClient getInstance() {
        if (mOkHttpClient == null) {
            synchronized (OkHttpManager.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(new HttpCacheInterceptor())
                            .readTimeout(15, TimeUnit.SECONDS)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * Request获取请求参数
     * @param request
     * @return
     */
    private static String getOriginalSign(Request request) {
        String url = request.url() + "";
        String sign_request_body = "";
        String method = request.method();
        if ("POST".equals(method))
            sign_request_body = bodyToString(request);
        else {
            String[] body = url.split("\\?");
            if (body.length > 1)
                sign_request_body = body[1];
        }
        return sign_request_body;
    }

    /**
     * Request获取请求参数
     *
     * @param request
     * @return
     */
    private static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    static class HttpCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            String token = (String) SharedPreferencesUtils.getInstance().getStringParam("token");
            Request request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            //打印请求信息
            Log.d("RetrofitLog:", "url:" + request.url());
            Log.d("RetrofitLog:", "headers:" + request.headers().toString());
            Log.d("RetrofitLog:", "method:" + request.method());

            //记录请求耗时
            long startNs = System.nanoTime();
            Response response;
            try {
                //发送请求，获得相应，
                response = chain.proceed(request);
            } catch (Exception e) {
                throw e;
            }
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            //打印请求耗时
            Log.d("RetrofitLog:", "耗时:" + tookMs + "ms");
            //使用response获得headers(),可以更新本地Cookie。
            Log.d("RetrofitLog:", "headers==========");
            Headers headers = response.headers();
            Log.d("RetrofitLog:", headers.toString());

            //获得返回的body，注意此处不要使用responseBody.string()获取返回数据，原因在于这个方法会消耗返回结果的数据(buffer)
            ResponseBody responseBody = response.body();

            //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            //获得返回的数据
            Buffer buffer = source.buffer();
            //使用前clone()下，避免直接消耗

            String s = buffer.clone().readString(Charset.forName("UTF-8"));
            Log.d("RetrofitLog:", "response:" + buffer.clone().readString(Charset.forName("UTF-8")));

            return response;
        }
    }
}
