package com.guet.shareapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;
import com.google.gson.Gson;
import com.guet.shareapp.domain.SimpleRespose;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {
    // 首先需要创建一个OkHttpClient对象用于Http请求, 可以改成全局型
    private OkHttpClient okHttpClient = new OkHttpClient();
    private OkHttpClient client = okHttpClient.newBuilder().build();
    String BaseUrl = "https://www.2020agc.site";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GetDemo();

        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 添加账号验证登录提示
                EditText username = findViewById(R.id.input_name);
                EditText password = findViewById(R.id.input_password);

                PostDemo_login(username.getText().toString(), password.getText().toString());

            }
        });

        TextView registerView = findViewById(R.id.newuser);
        registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
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
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, ret, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void PostDemo_login(String username, String password) {
        // post请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl + "/user/login";
                FormBody.Builder formBuilder = new FormBody.Builder();
                formBuilder.add("username", username);// 请求参数一
                formBuilder.add("password", password);// 请求参数二

                RequestBody requestBody = formBuilder.build();
                Request request = new Request.Builder().post(requestBody).url(url)
                        .post(requestBody).build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    SimpleRespose simpleRespose = new Gson().fromJson(response.body().string(), SimpleRespose.class);
                    System.out.println(simpleRespose.getMassage());
                    if (simpleRespose.getCode() == 200){
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                Toast.makeText(LoginActivity.this, simpleRespose.getMassage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (simpleRespose.getCode() == 400){
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, simpleRespose.getMassage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    response.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



}