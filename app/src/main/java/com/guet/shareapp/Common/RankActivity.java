package com.guet.shareapp.Common;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Adapter.SearchAdapter;
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
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RankActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String rank;
    SearchAdapter adapter;
    List<String> rank_lists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        rank = getIntent().getStringExtra("rank");
        initView();
        initData();

    }

    private void initView() {
        recyclerView = findViewById(R.id.rank_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }
    private void initData() {
        adapter = new SearchAdapter(this, rank_lists,rank);
        recyclerView.setAdapter(adapter);
        if(rank.equals("douban")){
            post_rank("picture/wallpaper_rank");
        }else{
            post_rank("picture/douban_rank");
        }

    }

    private void post_rank(String rank) {
        try {
            OkHttpUtils.post(rank, new HashMap<>(), new Callback()
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
                    if(json.contains("<title>404 Not Found</title>")){
                        ToastUtil.ShortToast("已经加载完毕");
                    }else{
                        Type type = new TypeToken<ResponseObject<List<String>>>(){}.getType();
                        ResponseObject<ArrayList<String>> responseObject = new Gson().fromJson(json, type);
                        if (responseObject.getCode() == 200 ) {
                            rank_lists.addAll(responseObject.getData());
                            new Handler(Looper.getMainLooper()).post(() -> {
                                adapter.notifyDataSetChanged();
                            });
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}