package com.guet.shareapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guet.shareapp.Interface.OnItemClickListener;
import com.guet.shareapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 首页分区adapter
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.Type_ViewHolder> {
    Context context;
    OnItemClickListener onItemClickListener;
    private List<String> list = new ArrayList<>();
    HashMap<String, Integer> mp = new HashMap<>();
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
    public TypeAdapter(Context context,List<String> list)
    {
        this.context = context;
        this.list = list;
//        mp.put("风景",R.drawable.ic_category_t59);
//        mp.put("美食",R.drawable.ic_category_t59);
//        list.add("风景");
//        list.add("美食");
//        list.add("人物");
//        list.add("萌宠");
//        list.add("动漫");
//        list.add("图案");
//        list.add("其它");
        //"番剧", "动画",
        // "音乐", "舞蹈", "游戏",
        //  "科技", "生活", "鬼畜",
        //   "时尚", "广告", "娱乐",
        //  "电影", "电视剧", "游戏中心"
        //"风景","美食","人物","萌宠","其它"

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TypeAdapter.Type_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_type, parent, false);
        final TypeAdapter.Type_ViewHolder holder = new TypeAdapter.Type_ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Type_ViewHolder itemViewHolder, int i) {
            itemViewHolder.mItemIcon.setImageResource(itemIcons[i]);
            itemViewHolder.mItemText.setText(list.get(i));
            itemViewHolder.mItemIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = itemViewHolder.getLayoutPosition();
                onItemClickListener.onItemClick(itemViewHolder.itemView,pos);
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
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
            mItemText = v.findViewById(R.id.item_title);
        }
    }



}
