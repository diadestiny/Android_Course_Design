package com.guet.shareapp.Common;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Adapter.DiscoverAdapter;
import com.guet.shareapp.Adapter.SearchAdapter;
import com.guet.shareapp.Entity.ImageEntity;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.OkHttpUtils;
import com.guet.shareapp.Utils.ToastUtil;
import com.guet.shareapp.domain.ResponseObject;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
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

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    SearchAdapter adapter;
    List<String> image_paths = new ArrayList<>();
    private int pageNum = 0;
    private int pageSize = 5;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activiry);
        initView();
        str = getIntent().getStringExtra("search");
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener()
        {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout)
            {
                try {
                    loadData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refreshlayout.finishLoadMore(400);//传入false表示加载失败
            }
        });
        recyclerView = findViewById(R.id.search_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }



    private void loadData() throws IOException {
        Map<String, String> map = new HashMap<>();
        ++pageNum;
        map.put("page", String.valueOf(pageNum));
        map.put("num", String.valueOf(pageSize));
        map.put("keyword", str);
        OkHttpUtils.post("picture/baidu_index", map, new Callback()
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
                        image_paths.addAll(responseObject.getData());
                        new Handler(Looper.getMainLooper()).post(() -> {
                            adapter.notifyDataSetChanged();
                        });
                    }
                }
            }
        });
    }

    private void initData() throws IOException{
        pageNum = 0; //重新初始化数据
        loadData();
        adapter = new SearchAdapter(this, image_paths,str);
        recyclerView.setAdapter(adapter);
    }

}