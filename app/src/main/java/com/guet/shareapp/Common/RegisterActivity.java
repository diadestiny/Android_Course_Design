package com.guet.shareapp.Common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.OkHttpUtils;
import com.guet.shareapp.Utils.ToastUtil;
import com.guet.shareapp.domain.ResponseObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
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
        EditText re_password = findViewById(R.id.input_reEnterPassword);
        EditText email = findViewById(R.id.input_email);
        EditText phone = findViewById(R.id.input_mobile);

        Button signup = findViewById(R.id.btn_signup);
        signup.setOnClickListener(v -> {
            if(username.getText().toString().equals("")||password.getText().toString().equals("")||email.getText().toString().equals("")||phone.getText().toString().equals("")) {
                ToastUtil.ShortToast("所填信息不能为空");
            }
            else if(!String.valueOf(password.getText()).equals(String.valueOf(re_password.getText()))){
                ToastUtil.ShortToast("两次密码输入不一致");
            }
            else{
                PostDemo_register(username.getText().toString(),password.getText().toString(),email.getText().toString(),phone.getText().toString());
            }
       });
    }

        private void PostDemo_register(String username, String password, String email, String phone) {
        // post请求
            HashMap<String,String> mp = new HashMap<>();
            mp.put("username", username);// 请求参数一
            mp.put("password", password);// 请求参数二
            mp.put("email", email);// 请求参数三
            mp.put("phone", phone);// 请求参数四
            try {
                OkHttpUtils.post("user/register", mp, new Callback()
                {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e)
                    {
                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                    {
                        ResponseObject responseObject = new Gson().fromJson(response.body().string(), ResponseObject.class);
                        if(responseObject.getCode() == 200){
                            ToastUtil.ShortToast("注册成功");
                            finish();
                        }else{
                            ToastUtil.ShortToast("注册失败");
                        }

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}