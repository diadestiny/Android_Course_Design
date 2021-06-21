package com.guet.shareapp.Fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Common.SearchActivity;
import com.guet.shareapp.Entity.HotTag;
import com.guet.shareapp.Interface.SearchService;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.OkHttpUtils;
import com.guet.shareapp.domain.ResponseObject;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecommendFragment extends Fragment {
    View view;
    EditText mSearchEdit;
    ImageView search_img;
    TagFlowLayout mHideTagLayout,mTagFlowLayout;
    TextView mMoreText;
    NestedScrollView mScrollView;
    private boolean isShowMore = true;
    private List<HotTag.ListBean> hotSearchTags = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSearchAPI()
                .getHotSearchTags()
                .map(HotTag::getList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBeans -> {
                    hotSearchTags.addAll(listBeans);
                    initTagLayout();
                }, throwable -> { });
    }

    private void initView() {
        mSearchEdit = view.findViewById(R.id.search_edit);
        search_img = view.findViewById(R.id.search_img);
        mHideTagLayout = view.findViewById(R.id.hide_tags_layout);
        mTagFlowLayout = view.findViewById(R.id.tags_layout);
        mMoreText = view.findViewById(R.id.tv_more);
        mScrollView = view.findViewById(R.id.hide_scroll_view);
        mMoreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowMore) {
                    isShowMore = false;
                    mScrollView.setVisibility(View.VISIBLE);
                    mMoreText.setText("收起");
                    mTagFlowLayout.setVisibility(View.GONE);
                    Drawable upDrawable = getResources().getDrawable(R.drawable.ic_arrow_up_gray_round);
                    upDrawable.setBounds(0, 0, upDrawable.getMinimumWidth(), upDrawable.getMinimumHeight());
                    mMoreText.setCompoundDrawables(upDrawable, null, null, null);
                } else {
                    isShowMore = true;
                    mScrollView.setVisibility(View.GONE);
                    mMoreText.setText("查看更多");
                    mTagFlowLayout.setVisibility(View.VISIBLE);
                    Drawable downDrawable = getResources().getDrawable(R.drawable.ic_arrow_down_gray_round);
                    downDrawable.setBounds(0, 0, downDrawable.getMinimumWidth(), downDrawable.getMinimumHeight());
                    mMoreText.setCompoundDrawables(downDrawable, null, null, null);
                }
            }
        });
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getContext(), SearchActivity.class);
               intent.putExtra("search",mSearchEdit.getText().toString());
               startActivity(intent);
            }
        });
    }

    private void initTagLayout() {
        //获取热搜标签集合前9个默认显示
        List<HotTag.ListBean> frontTags = hotSearchTags.subList(0, 8);
        mTagFlowLayout.setAdapter(new TagAdapter<HotTag.ListBean>(frontTags) {
            @Override
            public View getView(FlowLayout parent, int position, HotTag.ListBean listBean) {
                TextView mTags = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_tags_item, parent, false);
                mTags.setText(listBean.getKeyword());
                mTags.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), SearchActivity.class);
                        intent.putExtra("search",listBean.getKeyword());
                        startActivity(intent);
                    }
                });
                return mTags;
            }
        });
        mHideTagLayout.setAdapter(new TagAdapter<HotTag.ListBean>(hotSearchTags) {
            @Override
            public View getView(FlowLayout parent, int position, HotTag.ListBean listBean) {
                TextView mTags = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_tags_item, parent, false);
                mTags.setText(listBean.getKeyword());
                mTags.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), SearchActivity.class);
                        intent.putExtra("search",listBean.getKeyword());
                        startActivity(intent);
                    }
                });
                return mTags;
            }
        });
    }

    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpUtils.okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
    public static SearchService getSearchAPI() {
        return createApi(SearchService.class, "http://s.search.bilibili.com/");
    }




}
