package com.guet.shareapp.Common;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.gc.materialdesign.views.Button;
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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    // 首先需要创建一个OkHttpClient对象用于Http请求, 可以改成全局型
    public OkHttpClient okHttpClient = new OkHttpClient();
    public OkHttpClient client = okHttpClient.newBuilder().build();
    public String BaseUrl = "https://www.2020agc.site";
    public static String user_name = "";
    private EditText username;
    private EditText password;
    private Button loginBtn;
    private TextView registerView;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences=null;
    public static List<String> album_names = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                    ResponseObject responseObject = new Gson().fromJson(response.body().string(), ResponseObject.class);
                    System.out.println(responseObject.getMessage());
                    if (responseObject.getCode() == 200){
                        user_name = username;
                        keepData();
                        get_album_name();
                    }
                    else if (responseObject.getCode() == 400){
                        ToastUtil.ShortToast("登陆失败");
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

    private void get_album_name() {
        HashMap<String,String> show_album_map = new HashMap<>();

        show_album_map.put("username", LoginActivity.user_name);
        try {
            OkHttpUtils.post("picture/show_albums", show_album_map, new Callback()
            {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e)
                {

                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                {
                    ResponseBody responseBody = response.body();
                    assert responseBody != null;
                    String json = responseBody.string();
                    Type type = new TypeToken<ResponseObject<List<String>>>(){}.getType();
                    ResponseObject<ArrayList<String>> responseObject = new Gson().fromJson(json, type);
                    if (responseObject.getCode() == 200) {
                        album_names = responseObject.getData();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        ToastUtil.ShortToast("登陆成功");
                        Log.d("lkh","login："+album_names.toString());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}