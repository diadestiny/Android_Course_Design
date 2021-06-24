package com.guet.shareapp.Common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import okhttp3.Response;

public class FindPwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);
        EditText username = findViewById(R.id.input_name);
        EditText password = findViewById(R.id.input_password);
        EditText re_password = findViewById(R.id.input_reEnterPassword);
        EditText phone = findViewById(R.id.input_mobile);

        Button signup = findViewById(R.id.btn_signup);
        signup.setOnClickListener(v -> {
            if(username.getText().toString().equals("") ||phone.getText().toString().equals("")||password.getText().toString().equals("")) {
                ToastUtil.ShortToast("所填信息不能为空");
            }else if(!String.valueOf(password.getText()).equals(String.valueOf(re_password.getText()))){
                ToastUtil.ShortToast("两次密码输入不一致");
            }
            else{
                post_find_password(username.getText().toString(),password.getText().toString(),phone.getText().toString());
            }

        });

    }

    private void post_find_password(String username, String password, String phone) {
        HashMap<String,String> mp = new HashMap<>();
        mp.put("username", username);
        mp.put("password", password);
        mp.put("phone", phone);
        try {
            OkHttpUtils.post("user/retrieve_password", mp, new Callback()
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
                        ToastUtil.ShortToast("找回密码成功");
                        finish();
                    }else{
                        ToastUtil.ShortToast("用户名与手机号码不匹配");
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}