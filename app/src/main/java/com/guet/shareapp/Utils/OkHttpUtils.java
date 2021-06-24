package com.guet.shareapp.Utils;

import android.net.Uri;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public  class OkHttpUtils {
    /**
     * 请求基地址
     */
    public static final String BASE_URL="https://www.2020agc.site/";

    public static OkHttpClient okHttpClient=new OkHttpClient.Builder().cookieJar(new CookiesManager()).build();

    private static String token="";

    private static final String TAG = "OkhttpUtils";


    public static void setToken(String newtoken) {
        token = newtoken;
    }

    public static String getToken() {
        return token;
    }

    //普通的get请求
    public static void get(String url, Callback callback){
        Request.Builder builder = new Request.Builder()
                .url(OkHttpUtils.BASE_URL + url); //添加url
        Request request = builder.addHeader("token", OkHttpUtils.token).build();//携带token
        //构建一个参数的url
        final HttpUrl.Builder newBuilder = request.url().newBuilder();

        //构建完成
        Request last_request = builder.url(newBuilder.build()).build();
        okHttpClient.newCall(last_request).enqueue(callback);
    }

    /**
     * JSON数据格式请求
     * @param url
     *
     * @return
     */
    public static void post(String url,  Map<String,String> params, Callback callback) throws IOException {
        // 创建一个请求 Builder
        FormBody.Builder builder = new FormBody.Builder();

        for(String key:params.keySet()){
            builder.add(key, Objects.requireNonNull(params.get(key)));
        }
        RequestBody formBody = builder.build();
        // 创建一个 request
        Request request = new Request.Builder().url(OkHttpUtils.BASE_URL + url).post(formBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //上传头像
    public static  void post_head(String url, Map<String, String> uploadInfo, FileProgressRequestBody.ProgressListener listener, Callback callback) throws IOException {
        String username = uploadInfo.get("username");
        String filepath = uploadInfo.get("filepath");
        File file = new File(filepath);

        FileProgressRequestBody fileProgressRequestBody = new FileProgressRequestBody(file, "image/"+file.getName().substring(file.getName().lastIndexOf(".")), listener);

        MultipartBody build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",file.getName(), fileProgressRequestBody)
                .addFormDataPart("username", username)
                .build();

        Request.Builder builder = new Request.Builder();
        // 创建一个 request
        Request request = builder.url(BASE_URL+url).build();
        // 创建一个 Headers.Builder
        Headers.Builder headerBuilder = request.headers().newBuilder();
        headerBuilder.add("token",token);
        // 设置自定义的 builder
        builder.headers(headerBuilder.build()).post(build);
        okHttpClient.newCall(builder.build()).enqueue(callback);
    }

    public static void postPictureWithFile(String url , Map<String, String> uploadInfo, FileProgressRequestBody.ProgressListener listener, Callback callback)throws IOException {
        String username = uploadInfo.get("username");
        String intro = uploadInfo.get("intro");
        String visible = uploadInfo.get("visible");
        String albumName = uploadInfo.get("albumName");
        String filename = uploadInfo.get("filename");
        String filepath = uploadInfo.get("filepath");
        File file = new File(filepath);
//        Log.d("lkh", file.getName().substring(file.getName().lastIndexOf(".")));

        FileProgressRequestBody fileProgressRequestBody = new FileProgressRequestBody(file, "image/"+file.getName().substring(file.getName().lastIndexOf(".")), listener);

        MultipartBody build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", Uri.decode(filename+file.getName().substring(file.getName().lastIndexOf("."))), fileProgressRequestBody)
                .addFormDataPart("username", username)
                .addFormDataPart("intro", intro)
                .addFormDataPart("visible", visible)
                .addFormDataPart("albumName", albumName)
                .addFormDataPart("filename", filename)
                .build();

        Request.Builder builder = new Request.Builder();
        // 创建一个 request
        Request request = builder.url(BASE_URL+url).build();
        // 创建一个 Headers.Builder
        Headers.Builder headerBuilder = request.headers().newBuilder();
        headerBuilder.add("token",token);
        // 设置自定义的 builder
        builder.headers(headerBuilder.build()).post(build);
        okHttpClient.newCall(builder.build()).enqueue(callback);
    }

    public static class CookiesManager implements CookieJar {
        private Map<HttpUrl, List<Cookie>> cookieStore=new HashMap<>();


        private HttpUrl url; //上一个请求

        @NotNull
        @Override
        public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
            List<Cookie> cookies = cookieStore.get(url);

            return null != cookies ? cookies:new ArrayList<>();
        }

        @Override
        public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
            cookieStore.put(httpUrl,list);
            url=httpUrl;
        }
    }


}
