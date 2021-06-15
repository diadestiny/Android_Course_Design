package com.guet.shareapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Adapter.DiscoverAdapter;
import com.guet.shareapp.Adapter.OnItemClickListener;
import com.guet.shareapp.Adapter.PublishAdapter;
import com.guet.shareapp.Adapter.TypeAdapter;
import com.guet.shareapp.Common.AlbumDetailActivity;
import com.guet.shareapp.Common.LoginActivity;
import com.guet.shareapp.Entity.ImageEntity;
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

import static com.guet.shareapp.Common.LoginActivity.album_names;

public class TypeFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    TypeAdapter adapter;
    public static List<Integer> album_picture_ids = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_type, container, false);
        recyclerView = view.findViewById(R.id.recycle1);
        initRecyclerview();
        return view;
    }
    private void initRecyclerview() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapter = new TypeAdapter(getContext(), album_names);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    get_id(position);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
                Log.e("json", json);
                Type type = new TypeToken<ResponseObject<List<Integer>>>(){}.getType();
                ResponseObject<ArrayList<Integer>> responseObject = new Gson().fromJson(json, type);
                if (responseObject.getCode() == 200 ) {
                    album_picture_ids.clear();
                    album_picture_ids.addAll(responseObject.getData());
                    Intent intent = new Intent(getContext(), AlbumDetailActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
