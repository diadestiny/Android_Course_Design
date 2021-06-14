package com.guet.shareapp.Common;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.guet.shareapp.Adapter.OnItemClickListener;
import com.guet.shareapp.Adapter.PublishAdapter;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.ToastUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

public class PublishActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 100;
    int maxnum = 6;
    RecyclerView recyclerView;
    List<String> list = new ArrayList<>();
    private PublishAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        recyclerView = findViewById(R.id.mRecyclerview);
        list.add("add");
        initRecyclerview();
        initPermission();
    }
    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void initRecyclerview() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new PublishAdapter(this,list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position == list.size()-1){
                    if(list.size()>maxnum){
                        ToastUtil.ShortToast("当前选择图片数量达到上限");
                    }else{
                        selectPhoto(maxnum-position-1);
                    }
                }else{
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void selectPhoto(int num) {
        Matisse.from(this)
                .choose(MimeType.allOf())//图片类型
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(num)//可选的最大数
                .capture(true)//选择照片时，是否显示拍照
                .captureStrategy(new CaptureStrategy(true, "com.guet.shareapp.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .imageEngine(new GlideEngine())//图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE);//
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            for(Uri u:Matisse.obtainResult(data)){
               // if(list.size()>=2){
                  list.add(list.size()-1,u.toString());
              //  }else{
                //    list.add(0,u.toString());
               // }

            }
//            Log.d("lkh",Matisse.obtainResult(data).get(0).toString());
            adapter.notifyDataSetChanged();
        }
    }

    public void requestPermissions(String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    list.add(permissions[i]);
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        Toast.makeText(this, "没有开启权限将会导致部分功能不可使用", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            ActivityCompat.requestPermissions(this, list.toArray(new String[permissions.length]), 0);
        }
    }



}