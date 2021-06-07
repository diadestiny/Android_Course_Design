package com.guet.shareapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guet.shareapp.domain.SimpleRespose;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient = new OkHttpClient();
    private OkHttpClient client = okHttpClient.newBuilder().build();
    String BaseUrl = "https://www.2020agc.site";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username = findViewById(R.id.input_name);
        EditText password = findViewById(R.id.input_password);
        EditText email = findViewById(R.id.input_email);
        EditText phone = findViewById(R.id.input_mobile);

        Button signup = findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDemo_register(username.getText().toString(),
                        password.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString());
            }
        });



    }

    private void PostDemo_register(String username, String password, String email, String phone) {
        // post请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl + "/user/register";
                FormBody.Builder formBuilder = new FormBody.Builder();
                formBuilder.add("username", username);// 请求参数一
                formBuilder.add("password", password);// 请求参数二
                formBuilder.add("email", email);// 请求参数三
                formBuilder.add("phone", phone);// 请求参数四

                RequestBody requestBody = formBuilder.build();
                Request request = new Request.Builder().post(requestBody).url(url)
                        .post(requestBody).build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    SimpleRespose simpleRespose = new Gson().fromJson(response.body().string(), SimpleRespose.class);
                    System.out.println(simpleRespose.getMessage());
                    if (simpleRespose.getCode() == 200){
                        // 注册成功
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, simpleRespose.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (simpleRespose.getCode() == 400){
                        // 注册失败
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, simpleRespose.getMessage(), Toast.LENGTH_SHORT).show();
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