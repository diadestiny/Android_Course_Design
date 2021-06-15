package com.guet.shareapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guet.shareapp.Entity.ImageEntity;
import com.guet.shareapp.R;

import java.util.Arrays;
import java.util.List;


/**
 * Created by hcc on 2016/10/23 10:23
 * 100332338@qq.com
 * <p>
 * 首页分区adapter
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.Type_ViewHolder> {
    Context context;
    List<String> list;
    private String[] itemNames = new String[]{
            "直播", "番剧", "动画",
            "音乐", "舞蹈", "游戏",
            "科技", "生活", "鬼畜",
            "时尚", "广告", "娱乐",
            "电影", "电视剧", "游戏中心",
    };

    private int[] itemIcons = new int[]{
            R.drawable.ic_category_live, R.drawable.ic_category_t13,
            R.drawable.ic_category_t1, R.drawable.ic_category_t3,
            R.drawable.ic_category_t129, R.drawable.ic_category_t4,
            R.drawable.ic_category_t36, R.drawable.ic_category_t160,
            R.drawable.ic_category_t119, R.drawable.ic_category_t155,
            R.drawable.ic_category_t165, R.drawable.ic_category_t5,
            R.drawable.ic_category_t23, R.drawable.ic_category_t11,
            R.drawable.ic_category_game_center
    };
    public TypeAdapter(Context context)
    {
        this.context = context;
        list.addAll(Arrays.asList(itemNames));
    }

    @NonNull
    @Override
    public TypeAdapter.Type_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discover, parent, false);
        final TypeAdapter.Type_ViewHolder holder = new TypeAdapter.Type_ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Type_ViewHolder itemViewHolder, int i) {
            itemViewHolder.mItemIcon.setImageResource(itemIcons[i]);
            itemViewHolder.mItemText.setText(itemNames[i]);

    }



    @Override
    public int getItemCount() {
        return itemIcons.length;
    }

    static class Type_ViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;

        ImageView mItemIcon;
        TextView mItemText;

        public Type_ViewHolder(View v)
        {
            super(v);
            itemView = v;
            mItemIcon = v.findViewById(R.id.item_icon);
            mItemText = v.findViewById(R.id.tv_img_name);

        }
    }

}
