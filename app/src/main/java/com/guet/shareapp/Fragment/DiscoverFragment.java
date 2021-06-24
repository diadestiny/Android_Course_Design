package com.guet.shareapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Adapter.DiscoverAdapter;
import com.guet.shareapp.Common.LoginActivity;
import com.guet.shareapp.Common.MainActivity;
import com.guet.shareapp.Common.PublishActivity;
import com.guet.shareapp.Entity.ImageEntity;
import com.guet.shareapp.Interface.OnItemClickListener;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.OkHttpUtils;
import com.guet.shareapp.Utils.ToastUtil;
import com.guet.shareapp.domain.ResponseObject;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

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

public class DiscoverFragment extends Fragment {
    DiscoverAdapter adapter;
    View view;
    RecyclerView recyclerView;
    ArrayList<ImageEntity> discoverList;
    RefreshLayout refreshLayout;
    private int pageNum;
    private BottomSheetDialog dialog;
    private static final int pageSize = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discover, container, false);
        initView();
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initView() {
        refreshLayout = view.findViewById(R.id.refreshLayout_discover);
        refreshLayout.setOnRefreshListener(new OnRefreshListener()
        {
            @Override
            public void onRefresh(RefreshLayout refreshlayout)
            {
                try {
                    add_top_data();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(400);
            }
        });
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
                refreshlayout.finishLoadMore(400);
            }
        });
        recyclerView = view.findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        FloatingActionButton ftb = view.findViewById(R.id.float_btn);
        ftb.setOnClickListener(v -> startActivity( new Intent(getContext(), PublishActivity.class)));
    }

    private void add_top_data() throws IOException {
        Map<String, String> map = new HashMap<>();
        pageNum = 1;
        map.put("page", String.valueOf(pageNum));
        map.put("num", String.valueOf(pageSize));
        map.put("username",LoginActivity.user_name);
        OkHttpUtils.post("picture/index", map, new Callback()
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
                //Log.e("json", json);
                if(json.contains("<title>404 Not Found</title>")){
                    ToastUtil.ShortToast("已经加载完毕");
                }else{
                    Type type = new TypeToken<ResponseObject<List<ImageEntity>>>(){}.getType();
                    ResponseObject<ArrayList<ImageEntity>> responseObject = new Gson().fromJson(json, type);
                    if (responseObject.getCode() == 200 ) {
                        discoverList.clear();
                        discoverList.addAll(responseObject.getData());
                        new Handler(Looper.getMainLooper()).post(() -> {
                            adapter.notifyDataSetChanged();
                        });
                    }
                }
            }
        });
    }

    private void loadData() throws IOException {
        Map<String, String> map = new HashMap<>();
        ++pageNum;
        map.put("page", String.valueOf(pageNum));
        map.put("num", String.valueOf(pageSize));
        map.put("username",LoginActivity.user_name);
        OkHttpUtils.post("picture/index", map, new Callback()
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
                    Type type = new TypeToken<ResponseObject<List<ImageEntity>>>(){}.getType();
                    ResponseObject<ArrayList<ImageEntity>> responseObject = new Gson().fromJson(json, type);
                    if (responseObject.getCode() == 200 ) {
                        discoverList.addAll(responseObject.getData());
                        new Handler(Looper.getMainLooper()).post(() -> {
                            adapter.notifyDataSetChanged();
                        });
                    }
                }
            }
        });
    }
    private void initData() throws IOException {
        discoverList = new ArrayList<>();
        pageNum = 0; //重新初始化数据，避免保存上次的浏览记录导致再次切换时会在上一次的基础上进行刷新7
        loadData();
        adapter = new DiscoverAdapter(getContext(), discoverList);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showCommentDialog(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void showCommentDialog(int pos){
        dialog = new BottomSheetDialog(getContext());
        View commentView = LayoutInflater.from(getContext()).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){
                    Map<String, String> map = new HashMap<>();
                    map.put("username", LoginActivity.user_name);
                    map.put("pic_id", discoverList.get(pos).getImgID());
                    map.put("content", commentText.getText().toString());
                    try {
                        OkHttpUtils.post("comment/add", map, new Callback()
                        {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e)
                            {

                            }
                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                            {
                               ToastUtil.ShortToast("发布评论成功");
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }else {
                    ToastUtil.ShortToast("评论不能为空");
                }
            }
        });
        dialog.show();
    }

}
