package com.guet.shareapp.Common;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Interface.OnItemClickListener;
import com.guet.shareapp.Adapter.PublishAdapter;
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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.guet.shareapp.Fragment.TypeFragment.album_names;

public class PublishActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 100;
    int maxnum = 1;
    RecyclerView recyclerView;
    List<String> list = new ArrayList<>();
    Spinner spinner;
    EditText editPicName,editPicInfo;
    CheckBox checkBox;
    Button publish_btn;
    TextView typeName;

    private PublishAdapter adapter;
    List<String> albumNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        recyclerView = findViewById(R.id.mRecyclerview);
        spinner = findViewById(R.id.spin);
        editPicName = findViewById(R.id.etpic);
        editPicInfo = findViewById(R.id.etDescribe);
        checkBox = findViewById(R.id.choose);
        publish_btn = findViewById(R.id.publish);
        typeName = findViewById(R.id.typeName);

        publish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    upload();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        setSpinner();
        initRecyclerview();
        initPermission();
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
                        albumNames = responseObject.getData();
//                        Log.d("lkh",albumNames.toString());
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSpinner() {

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, album_names.subList(0,album_names.size()-1));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeName.setText(album_names.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void upload() throws IOException {
        if (editPicName.getText().toString().equals("")){
            ToastUtil.ShortToast("????????????????????????");
        }else{

            HashMap map=new HashMap<String,String>();
            map.put("username", LoginActivity.user_name);

            map.put("intro",editPicInfo.getText().toString());
            if(checkBox.isChecked()){
                map.put("visible","true");
            }else{
                map.put("visible","false");
            }
            map.put("albumName",typeName.getText().toString());

            map.put("filename",editPicName.getText().toString());
            map.put("filepath",list.get(0));
            //????????????
            OkHttpUtils.postPictureWithFile("picture/upload", map, listener, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    ToastUtil.ShortToast("????????????");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    ResponseBody responseBody = response.body();
                    assert responseBody != null;
                    String json = responseBody.string();
//                    Log.e("lkh", json);
                    if(json.contains("413 Request Entity")){
                        ToastUtil.ShortToast("???????????????????????????");
                    }else{
                        ResponseObject responeObject = new Gson().fromJson(json, ResponseObject.class);
                        if (responeObject.getCode() == 200){
                            Log.e("lkh","??????");
                            ToastUtil.ShortToast("????????????");
                            finish();
                        }else {
                            Log.e("lkh", "??????");
                            ToastUtil.ShortToast("????????????");
                        }
                    }

                }
            });

        }
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void initRecyclerview() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new PublishAdapter(this,list);
        adapter.setOnItemClickListener1(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                    if(list.size()>maxnum){
                        ToastUtil.ShortToast("????????????????????????????????????");
                    }else{
                        selectPhoto(maxnum-position-1);
                        adapter.notifyDataSetChanged();
                    }
            }
        });
        adapter.setOnItemClickListener2(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void selectPhoto(int num) {
        Matisse.from(this)
                .choose(MimeType.allOf())//????????????
                .countable(true)//true:?????????????????????;false:?????????????????????
                .maxSelectable(num)//??????????????????
                .capture(true)//????????????????????????????????????
                .captureStrategy(new CaptureStrategy(true, "com.guet.shareapp.fileprovider"))//??????1 true????????????????????????????????????false????????????????????????????????????2??? AndroidManifest???authorities????????????????????????7.0?????? ????????????
                .imageEngine(new GlideEngine())//??????????????????
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            for(Uri u:Matisse.obtainResult(data)){
                  list.add(getRealFilePath(this,u));
            }
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
                        Toast.makeText(this, "??????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            ActivityCompat.requestPermissions(this, list.toArray(new String[permissions.length]), 0);
        }
    }

    private FileProgressRequestBody.ProgressListener listener= v->{
        int i = (int) (v * 100);
        if (i == 100){
//            ToastUtil.ShortToast("????????????");
        }
    };
    /**
     *  ??????Uri????????????????????????
     */
    public static String getRealFilePath(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String realPath = null;
        if (scheme == null)
            realPath = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            realPath = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA},
                    null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        realPath = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        if (TextUtils.isEmpty(realPath)) {
            if (uri != null) {
                String uriString = uri.toString();
                int index = uriString.lastIndexOf("/");
                String imageName = uriString.substring(index);
                File storageDir;

                storageDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                File file = new File(storageDir, imageName);
                if (file.exists()) {
                    realPath = file.getAbsolutePath();
                } else {
                    storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    File file1 = new File(storageDir, imageName);
                    realPath = file1.getAbsolutePath();
                }
            }
        }
        return realPath;
    }

}