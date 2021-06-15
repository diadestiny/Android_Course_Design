package com.guet.shareapp.Common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.OkHttpUtils;
import com.guet.shareapp.Utils.ToastUtil;
import com.guet.shareapp.domain.ResponseObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SplashActivity extends AppCompatActivity {

    public OkHttpClient okHttpClient = new OkHttpClient();
    public OkHttpClient client = okHttpClient.newBuilder().build();
    public String BaseUrl = "https://www.2020agc.site";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GetDemo();
    }
    private void GetDemo() {
        // get请求
        // 创建一个request对象
        Request request = new Request.Builder().url(BaseUrl).build();
        // 执行和回调
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                // 请求失败
            }
            public void onResponse(Call call, Response response)
                    throws IOException {
                // 请求成功
                String ret = response.body().string();
                // 访问UI线程
                ToastUtil.ShortToast("连接服务器成功");
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}