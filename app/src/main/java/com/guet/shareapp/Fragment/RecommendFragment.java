package com.guet.shareapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Common.AlbumActivity;
import com.guet.shareapp.Common.LoginActivity;
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

public class RecommendFragment extends Fragment {
    View view;
    EditText mSearchEdit;
    ImageView search_img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        initView();
        return view;
    }

    private void initView() {
        mSearchEdit = view.findViewById(R.id.search_edit);
        search_img = view.findViewById(R.id.search_img);
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Map<String, String> map = new HashMap<>();
//                map.put("username", LoginActivity.user_name);
//                map.put("albumName",album_names.get(pos));
//                OkHttpUtils.post("picture/baidu_index", map, new Callback()
//                {
//                    @Override
//                    public void onFailure(@NotNull Call call, @NotNull IOException e)
//                    {
//                        ToastUtil.ShortToast("该相册打开失败");
//                    }
//                    @Override
//                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
//                    {
//                        ResponseBody responseBody = response.body();
//                        assert responseBody != null;
//                        String json = responseBody.string();
//                        Log.e("json", json);
//                        Type type = new TypeToken<ResponseObject<List<Integer>>>(){}.getType();
//                        ResponseObject<ArrayList<Integer>> responseObject = new Gson().fromJson(json, type);
//                        if (responseObject.getCode() == 200 ) {
//                            album_picture_ids.clear();
//                            album_picture_ids.addAll(responseObject.getData());
//                            Intent intent = new Intent(getContext(), AlbumActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//                });
            }
        });
    }

}
