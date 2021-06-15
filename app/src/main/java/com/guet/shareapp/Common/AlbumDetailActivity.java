package com.guet.shareapp.Common;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Adapter.Album_detail_Adapter;
import com.guet.shareapp.Adapter.DiscoverAdapter;
import com.guet.shareapp.Adapter.OnItemClickListener;
import com.guet.shareapp.Adapter.PublishAdapter;
import com.guet.shareapp.Adapter.TypeAdapter;
import com.guet.shareapp.Entity.AlbumEntity;
import com.guet.shareapp.Entity.ImageEntity;
import com.guet.shareapp.Fragment.TypeFragment;
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

public class AlbumDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Album_detail_Adapter adapter;
    List<String> album_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        recyclerView = findViewById(R.id.id_album);
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initData() throws IOException {
        album_list = new ArrayList<>();
        for(int i=0; i<TypeFragment.album_picture_ids.size(); i++){
            album_list.add("https://www.2020agc.site/picture/show_picture/"+TypeFragment.album_picture_ids.get(i));
        }
        adapter = new Album_detail_Adapter(this, album_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }
}