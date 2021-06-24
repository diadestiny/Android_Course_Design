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
import com.guet.shareapp.Interface.OnItemLongListener;
import com.guet.shareapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 首页相册adapter
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.Type_ViewHolder> {
    Context context;
    OnItemClickListener onItemClickListener;
    OnItemLongListener onItemLongListener;
    private List<String> list = new ArrayList<>();
    private int[] itemIcons = new int[]{
            R.drawable.ic_category_live, R.drawable.ic_category_t13,
            R.drawable.ic_category_t3, R.drawable.ic_category_t129,
            R.drawable.ic_category_t4, R.drawable.ic_category_t36,
            R.drawable.ic_category_t160, R.drawable.ic_category_t119,
            R.drawable.ic_category_t155, R.drawable.ic_category_t165,
            R.drawable.ic_category_t5, R.drawable.ic_category_t23,
            R.drawable.ic_category_t11, R.drawable.ic_category_game_center,
            R.drawable.ic_category_t26,
    };
    public TypeAdapter(Context context,List<String> list)
    {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongListener(OnItemLongListener onItemLongListener) {
        this.onItemLongListener = onItemLongListener;
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
        if(i == list.size()-1){
            itemViewHolder.mItemIcon.setImageResource(R.mipmap.ic_add);
        }else{
            itemViewHolder.mItemIcon.setImageResource(itemIcons[i]);
            itemViewHolder.mItemIcon.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = itemViewHolder.getLayoutPosition();
                    onItemLongListener.onItemLongClick(itemViewHolder.itemView,pos);
                    return true;
                }
            });
        }
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