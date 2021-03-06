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
                        .choose(MimeType.allOf())//????????????
                        .countable(true)//true:?????????????????????;false:?????????????????????
                        .maxSelectable(1)//??????????????????
                        .capture(true)//????????????????????????????????????
                        .captureStrategy(new CaptureStrategy(true, "com.guet.shareapp.fileprovider"))//??????1 true????????????????????????????????????false????????????????????????????????????2??? AndroidManifest???authorities????????????????????????7.0?????? ????????????
                        .imageEngine(new GlideEngine())//??????????????????
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });
    }

    private void save() {
        if(tvUsername.getText().equals("")||tvEmail.getText().equals("")||tvPhoneNumber.getText().equals("")||tvInfo.getText().equals("")){
            ToastUtil.ShortToast("????????????????????????");
        }else if (!isEmail(tvEmail.getText().toString().trim()) && tvEmail.getText().toString().trim().length()<=31){
            ToastUtil.ShortToast("??????????????????");
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
                        ToastUtil.ShortToast("????????????");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //?????????????????????
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
                            ToastUtil.ShortToast("????????????");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            ResponseBody responseBody = response.body();
                            assert responseBody != null;
                            String json = responseBody.string();
                            if(json.contains("413 Request Entity")){
                                ToastUtil.ShortToast("???????????????????????????");
                            }else{
                                ResponseObject responseObject = new Gson().fromJson(json, ResponseObject.class);
                                if (responseObject.getCode() == 200){
                                    ToastUtil.ShortToast("??????????????????");
                                }else {
                                    ToastUtil.ShortToast("??????????????????");
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
//            ToastUtil.ShortToast("????????????");
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