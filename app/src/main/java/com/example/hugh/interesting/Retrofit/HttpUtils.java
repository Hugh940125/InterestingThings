package com.example.hugh.interesting.Retrofit;

import com.alibaba.fastjson.JSONObject;

import okhttp3.RequestBody;


public class HttpUtils {
    public static final KernelService mService = KernelFactory.getKernelApi();

    public static RequestBody getSpecialRequestBody(JSONObject jsonObject) {
        String s = jsonObject.toJSONString();
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
    }

    public static RequestBody getRequestBody(JSONObject params) {
//        params.put("appKey", app_key);
//        params.put("timestamp", SignTools.getTimeStamp());
//        String sign = SignTools.getSign(params, app_secret);
        JSONObject jsonObject = new JSONObject();
        for (String key : params.keySet()) {
            jsonObject.put(key, params.get(key));
        }
//        jsonObject.put("sign", sign);
        String s = jsonObject.toString();
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
    }
}
