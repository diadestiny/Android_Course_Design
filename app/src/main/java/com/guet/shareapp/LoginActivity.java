package com.guet.shareapp;


import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;
import com.guet.shareapp.domain.SimpleRespose;
import com.guet.shareapp.netUtils.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // 创建
    private API api = RetrofitManager.getRetrofit().create(API.class);
    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getHello();

        Button loginBtn = findViewById(R.id.login_btn);

        String username = findViewById(R.id.input_name).toString();
        String password = findViewById(R.id.input_password).toString();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call<SimpleRespose> task = api.login(username, password);
                        task.enqueue(new Callback<SimpleRespose>() {
                            @Override
                            public void onResponse(Call<SimpleRespose> call, Response<SimpleRespose> response) {
                                SimpleRespose ret = response.body();
                                assert ret != null;
                                int code = ret.getCode();
                                String massage =  ret.getMassage();
                                Toast.makeText(LoginActivity.this, code + ":" + massage, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<SimpleRespose> call, Throwable t) {
                                Log.d(TAG, "fail: " + t.toString());
                            }
                        });


                    }
                }).start();

                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });
    }

    // 测试链接
    public void getHello(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<String> task = api.getHello();
                task.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String ret = response.body();
                        assert ret != null;

                        Toast.makeText(LoginActivity.this, ret, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "fail: " + t.toString());
                    }
                });


            }
        }).start();

    }

}