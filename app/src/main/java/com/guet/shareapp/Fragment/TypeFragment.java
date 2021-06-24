package com.guet.shareapp.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Common.MainActivity;
import com.guet.shareapp.Interface.OnItemClickListener;
import com.guet.shareapp.Adapter.TypeAdapter;
import com.guet.shareapp.Common.AlbumActivity;
import com.guet.shareapp.Common.LoginActivity;
import com.guet.shareapp.Interface.OnItemLongListener;
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



public class TypeFragment extends Fragment implements Runnable{
    View view;
    RecyclerView recyclerView;
    TypeAdapter adapter;
    public static List<String> album_names = new ArrayList<>();
    final static int MAX_NUM = 15;
    public static List<Integer> album_picture_ids = new ArrayList<>();


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    // 更新相册
                    album_names.add("添加");
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    // 删除相册
                    adapter.notifyDataSetChanged();
                    break;

            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_type, container, false);
        recyclerView = view.findViewById(R.id.recycle1);
        initRecyclerview();
        new Thread(this).start();
        return view;
    }


    private void initRecyclerview() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapter = new TypeAdapter(getContext(), album_names);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position == album_names.size()-1){
                    final EditText input = new EditText(getContext());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("请输入新的相册名").setIcon(android.R.drawable.ic_dialog_info).setView(input)
                            .setNegativeButton("取消", null);
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            create_album(input.getText().toString());
                        }
                    });
                    builder.show();
                }else{
                    try {
                        get_id(position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        adapter.setOnItemLongListener((view, position) -> {
            if(position != album_names.size()-1){
                AlertDialog alertDialog2 = new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("是否删除该相册")
                        .setIcon(R.drawable.logo)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                delete_album(position);
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
    }

    private void delete_album(int pos) {
        HashMap<String,String> album_map = new HashMap<>();
        album_map.put("username", LoginActivity.user_name);
        album_map.put("albumName", album_names.get(pos));
        try {
            OkHttpUtils.post("picture/del_album", album_map, new Callback()
            {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e)
                {

                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                {
                    album_names.remove(pos);
                    Message  msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void get_id(int pos) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("username", LoginActivity.user_name);
        map.put("albumName",album_names.get(pos));
        OkHttpUtils.post("picture/show_album_pictures", map, new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                ToastUtil.ShortToast("该相册打开失败");
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                ResponseBody responseBody = response.body();
                assert responseBody != null;
                String json = responseBody.string();
//                Log.e("json", json);
                Type type = new TypeToken<ResponseObject<List<Integer>>>(){}.getType();
                ResponseObject<ArrayList<Integer>> responseObject = new Gson().fromJson(json, type);
                if (responseObject.getCode() == 200 ) {
                    album_picture_ids.clear();
                    album_picture_ids.addAll(responseObject.getData());
                    Intent intent = new Intent(getContext(), AlbumActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void run() {
        get_album_name();
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
                        album_names.clear();
                        album_names.addAll(responseObject.getData());
                        Message  msg = new Message();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void create_album(String name) {
        if(album_names.size()>MAX_NUM){
            ToastUtil.ShortToast("相册已达上限,创建失败");
            return;
        }
        HashMap<String,String> album_map = new HashMap<>();
        album_map.put("username", LoginActivity.user_name);
        album_map.put("albumName", name);
        try {
            OkHttpUtils.post("picture/create_album", album_map, new Callback()
            {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e)
                {

                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                {
                    album_names.remove(album_names.size()-1);
                    album_names.add(name);
                    Message  msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
