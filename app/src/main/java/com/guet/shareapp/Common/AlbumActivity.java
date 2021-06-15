package com.guet.shareapp.Common;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.guet.shareapp.Adapter.AlbumAdapter;
import com.guet.shareapp.Fragment.TypeFragment;
import com.guet.shareapp.Interface.OnItemLongListener;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.OkHttpUtils;
import com.guet.shareapp.Utils.ToastUtil;
import com.guet.shareapp.domain.ResponseObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AlbumActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AlbumAdapter adapter;
    List<String> album_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        recyclerView = findViewById(R.id.id_album);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void remove_pic(int id)  {
        Map<String, String> map = new HashMap<>();
        map.put("pic_id", String.valueOf(id));
        try {
            OkHttpUtils.post("picture/del", map, new Callback()
            {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e)
                {
                    ToastUtil.ShortToast("删除失败");
                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                {
                    String json = response.body().string();
                    Log.e("lkh",json);
                    ResponseObject responseObject = new Gson().fromJson(json, ResponseObject.class);
                    if (responseObject.getCode() == 200 ) {
                        ToastUtil.ShortToast("删除成功");
                    }else{
                        ToastUtil.ShortToast("删除失败");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void init() throws IOException {
        album_list = new ArrayList<>();
        for(int i=0; i<TypeFragment.album_picture_ids.size(); i++){
            album_list.add("https://www.2020agc.site/picture/show_picture/"+TypeFragment.album_picture_ids.get(i));
        }
        adapter = new AlbumAdapter(this, album_list);
        adapter.setOnItemLongListener(new OnItemLongListener() {
            @Override
            public void onItemLongClick(View view, int position) {

                AlertDialog alertDialog2 = new AlertDialog.Builder(AlbumActivity.this)
                        .setTitle("提示")
                        .setMessage("是否删除该图片")
                        .setIcon(R.drawable.logo)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("lkh",TypeFragment.album_picture_ids.get(position)+"");
                                remove_pic(TypeFragment.album_picture_ids.get(position));
                                album_list.remove(position);
                                adapter.notifyDataSetChanged();
                                ToastUtil.ShortToast("删除成功");

                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                alertDialog2.show();

            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);

    }
}