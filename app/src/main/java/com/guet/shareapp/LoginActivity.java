package com.guet.shareapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.gc.materialdesign.views.Button;
import com.google.gson.Gson;
import com.guet.shareapp.domain.SimpleResponse;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    // 首先需要创建一个OkHttpClient对象用于Http请求, 可以改成全局型
    private OkHttpClient okHttpClient = new OkHttpClient();
    private OkHttpClient client = okHttpClient.newBuilder().build();
    String BaseUrl = "https://www.2020agc.site";
    public static String user_name = "";
    private EditText username;
    private EditText password;
    private Button loginBtn;
    private TextView registerView;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GetDemo();
        loginBtn = findViewById(R.id.login_btn);
        registerView = findViewById(R.id.newuser);
        username = findViewById(R.id.input_name);
        password = findViewById(R.id.input_password);
        checkBox = findViewById(R.id.check_box);
        loginBtn.setOnClickListener(this);
        registerView.setOnClickListener(this);
        initData();

    }

    private void initData() {
        if(sharedPreferences==null){
            sharedPreferences = getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        String str_user_name = sharedPreferences.getString("sp_account","");
        String str_password = sharedPreferences.getString("sp_password","");
        username.setText(str_user_name);
        password.setText(str_password);
        checkBox.setChecked(sharedPreferences.getBoolean("sp_remember",false));

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
                    SimpleResponse simpleResponse = new Gson().fromJson(response.body().string(), SimpleResponse.class);
                    System.out.println(simpleResponse.getMessage());
                    if (simpleResponse.getCode() == 200){
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                user_name = username;
                                keepData();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                Toast.makeText(LoginActivity.this, simpleResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (simpleResponse.getCode() == 400){
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, simpleResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                // 添加账号验证登录提示
                if(username.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast.makeText(this,"用戶名或密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    PostDemo_login(username.getText().toString(), password.getText().toString());
                }
               break;
            case R.id.newuser:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    private void keepData(){
        if(sharedPreferences==null) {
            sharedPreferences = getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(checkBox.isChecked()){
            editor.putString("sp_account",username.getText().toString());
            editor.putString("sp_password",password.getText().toString());
            editor.putBoolean("sp_remember",true);
        }
        else{
            editor.putString("sp_account","");
            editor.putString("sp_password","");
            editor.putBoolean("sp_remember",false);
        }
        editor.apply();
    }

}