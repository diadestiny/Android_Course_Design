package com.guet.shareapp.Common;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Adapter.PublishAdapter;
import com.guet.shareapp.Entity.UserInfo;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.FileProgressRequestBody;
import com.guet.shareapp.Utils.OkHttpUtils;
import com.guet.shareapp.Utils.ToastUtil;
import com.guet.shareapp.domain.ResponseObject;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.guet.shareapp.Common.PublishActivity.getRealFilePath;

public class MyInfoActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 200;
    private ImageView ivHeadIcon;
    private EditText tvUsername;
    private EditText tvEmail;
    private EditText tvPhoneNumber;
    private EditText tvInfo;
    private Button save_btn;
    private UserInfo user;
    private Handler handler = new Handler();

    public static Pattern p =
            Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        initView();
        initData();
    }


    private void initData() {
        Glide.with(MyInfoActivity.this)
                .load("https://www.2020agc.site/user/show_avatar/"+LoginActivity.user_name)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .into(ivHeadIcon);
        HashMap<String,String> tmp = new HashMap<>();
        tmp.put("username", LoginActivity.user_name);
        Thread t= new Thread(){
            @Override
            public void run() {
                try {
                    OkHttpUtils.post("user/show_user_info", tmp, new Callback()
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
                            Type type = new TypeToken<ResponseObject<UserInfo>>(){}.getType();
                            ResponseObject<UserInfo> responseObject = new Gson().fromJson(json, type);
                            if (responseObject.getCode() == 200) {
                                user = responseObject.getData();
                                handler.post(runnableUI);
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();

    }

    private void initView() {
        ivHeadIcon = findViewById(R.id.iv_head);
        tvUsername = findViewById(R.id.user_name);
        tvEmail = findViewById(R.id.email);
        tvPhoneNumber = findViewById(R.id.number);
        tvInfo = findViewById(R.id.my_info);
        save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        ivHeadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse.from(MyInfoActivity.this)
                        .choose(MimeType.allOf())//图片类型
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(1)//可选的最大数
                        .capture(true)//选择照片时，是否显示拍照
                        .captureStrategy(new CaptureStrategy(true, "com.guet.shareapp.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                        .imageEngine(new GlideEngine())//图片加载引擎
                        .forResult(REQUEST_CODE_CHOOSE);//
            }
        });
    }

    private void save() {
        if(tvUsername.getText().equals("")||tvEmail.getText().equals("")||tvPhoneNumber.getText().equals("")||tvInfo.getText().equals("")){
            ToastUtil.ShortToast("所填信息不能为空");
        }else if (!isEmail(tvEmail.getText().toString().trim()) && tvEmail.getText().toString().trim().length()<=31){
            ToastUtil.ShortToast("邮箱格式有误");
        }else {
            HashMap<String,String> album_map = new HashMap<>();
            album_map.put("username", LoginActivity.user_name);
            album_map.put("nickname", tvUsername.getText().toString());
            album_map.put("selfIntro", tvInfo.getText().toString());
            album_map.put("email", tvEmail.getText().toString());
            album_map.put("phone", tvPhoneNumber.getText().toString());
            try {
                OkHttpUtils.post("user/change_user_info", album_map, new Callback()
                {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e)
                    {

                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                    {
                        ToastUtil.ShortToast("修改成功");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //验证函数优化版
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            for(Uri u:Matisse.obtainResult(data)){
                Glide.with(getApplicationContext())
                        .load(u)
                        .fitCenter()
                        .into(ivHeadIcon);

                HashMap map=new HashMap<String,String>();
                map.put("username", LoginActivity.user_name);
                map.put("filepath",getRealFilePath(this,u));
                try {
                    OkHttpUtils.post_head("user/upload_avatar", map, listener,new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            ToastUtil.ShortToast("上传失败");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            ResponseBody responseBody = response.body();
                            assert responseBody != null;
                            String json = responseBody.string();
//                            Log.e("lkh", json);
                            if(json.contains("413 Request Entity")){
                                ToastUtil.ShortToast("图片过大，无法上传");
                            }else{
                                ResponseObject responseObject = new Gson().fromJson(json, ResponseObject.class);
                                if (responseObject.getCode() == 200){
                                    ToastUtil.ShortToast("上传头像成功");
                                }else {
                                    ToastUtil.ShortToast("上传头像失败");
                                }
                            }

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private FileProgressRequestBody.ProgressListener listener= v->{
        int i = (int) (v * 100);
        if (i == 100){
//            ToastUtil.ShortToast("上传成功");
        }
    };

    Runnable runnableUI = new Runnable() {
        @Override
        public void run() {
            tvEmail.setText(user.getEmail());
            tvPhoneNumber.setText(user.getPhone());
            tvInfo.setText(user.getSelfIntro());
            tvUsername.setText(user.getNickname());
        }
    };

}