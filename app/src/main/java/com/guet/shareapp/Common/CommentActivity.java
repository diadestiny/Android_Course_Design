package com.guet.shareapp.Common;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Adapter.CommentAdapter;
import com.guet.shareapp.Entity.CommentEntity;
import com.guet.shareapp.Entity.Detail;
import com.guet.shareapp.Entity.UserInfo;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.OkHttpUtils;
import com.guet.shareapp.domain.ResponseObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CommentActivity extends AppCompatActivity {
    RecyclerView recycle;
    ImageView iv_author;
    TextView tv_name_author;
    TextView tv_name_img;
    TextView tv_info;
    TextView tv_time;
    TextView tv_star_num;
    ImageView iv_img;
    Handler handler = new Handler();
    Detail detail;
    CommentAdapter adapter;
    List<CommentEntity> commentEntityList = new ArrayList<>();

    int pic_id;
    String img_url,author_url,name_author,name_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        pic_id = Integer.parseInt(getIntent().getStringExtra("pic_id"));
        img_url = getIntent().getStringExtra("img_url");
        author_url = getIntent().getStringExtra("author_url");
        name_author = getIntent().getStringExtra("tv_name_author");
        name_img = getIntent().getStringExtra("tv_name_img");
        initView();
        initData();
    }

    private void initData() {
        Glide.with(this)
                .load(img_url)
                .error(R.drawable.ic_load)
                .fitCenter()
                .into(iv_img);
        Glide.with(this)
                .load(author_url)
                .error(R.drawable.head)
                .fitCenter()
                .into(iv_author);

        tv_name_author.setText(name_author);
        tv_name_img.setText(name_img);

        Thread t= new Thread(){
            @Override
            public void run() {
                try {
                    HashMap<String,String> tmp = new HashMap<>();
                    tmp.put("pic_id", String.valueOf(pic_id));
                    OkHttpUtils.post("picture/show_picture_info", tmp, new Callback()
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
                            Type type = new TypeToken<ResponseObject<Detail>>(){}.getType();
                            ResponseObject<Detail> responseObject = new Gson().fromJson(json, type);
                            if (responseObject.getCode() == 200) {
                                detail = responseObject.getData();
                                handler.post(runnableUI1);
                            }
                        }
                    });

                    OkHttpUtils.get("comment/show_comment/"+ pic_id, new Callback()
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
                            Log.d("lkh",json);
                            Type type = new TypeToken<ResponseObject<List<CommentEntity>>>(){}.getType();
                            ResponseObject<List<CommentEntity>> responseObject = new Gson().fromJson(json, type);
                            if (responseObject.getCode() == 200) {
                                commentEntityList.addAll(responseObject.getData());
                                handler.post(runnableUI2);
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
        iv_author = findViewById(R.id.iv_author);
        iv_img = findViewById(R.id.iv_img);
        tv_name_author = findViewById(R.id.tv_name_author);
        tv_name_img = findViewById(R.id.tv_name_img);
        tv_info = findViewById(R.id.tv_info);
        tv_time = findViewById(R.id.tv_time);
        tv_star_num = findViewById(R.id.tv_star_num);
        recycle = findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(manager);

    }

    Runnable runnableUI1 = new Runnable() {
        @Override
        public void run() {
            tv_info.setText(detail.getPictureIntro());
            tv_time.setText(detail.getCreated());
            tv_star_num.setText(String.valueOf(detail.getStarSum()));
        }
    };

    Runnable runnableUI2 = new Runnable() {
        @Override
        public void run() {
            adapter = new CommentAdapter(CommentActivity.this,commentEntityList);
            recycle.setAdapter(adapter);
        }
    };
}