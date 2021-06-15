package com.guet.shareapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guet.shareapp.Entity.ImageEntity;
import com.guet.shareapp.R;

import java.util.List;

public class PublishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PIC_ITEM = 0;
    private static final int ADD_ITEM = 1;

    List<String> list;
    Context context;
    OnItemClickListener onItemClickListener1,onItemClickListener2;
    private int maxNum = 1;

    public void setOnItemClickListener1(OnItemClickListener monItemClickListener) {
        this.onItemClickListener1 = monItemClickListener;
    }

    public void setOnItemClickListener2(OnItemClickListener monItemClickListener) {
        this.onItemClickListener2 = monItemClickListener;
    }

    public PublishAdapter(Context context, List<String>List)
    {
        this.list = List;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if(i == PIC_ITEM){
            view =  LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
            return new ViewHolder_item(view);
        }else{
            view =  LayoutInflater.from(context).inflate(R.layout.add_item, viewGroup, false);
            return new ViewHolder_add(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof ViewHolder_add){
            ((ViewHolder_add) viewHolder).ImgView.setImageResource(R.mipmap.ic_add);
            ((ViewHolder_add) viewHolder).ImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    onItemClickListener1.onItemClick(viewHolder.itemView,pos);
                }
            });
        }else if (viewHolder instanceof ViewHolder_item){
            Glide.with(context)
                    .load(list.get(i))
                    .into(((ViewHolder_item) viewHolder).ImgView);
            ((ViewHolder_item) viewHolder).delView.setImageResource(R.mipmap.pic_del);
            ((ViewHolder_item) viewHolder).delView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    onItemClickListener2.onItemClick(viewHolder.itemView,pos);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(list.size() == maxNum){
            return PIC_ITEM;
        }else{
            if (position + 1 == getItemCount()) {
                return ADD_ITEM;
            } else {
                return PIC_ITEM;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(list.size()<maxNum){
            return list.size()+1;
        }else{
            return list.size();
        }
    }

    static class ViewHolder_item extends RecyclerView.ViewHolder
    {
        ImageView ImgView;
        ImageView delView;
        public ViewHolder_item(View itemView)
        {
            super(itemView);
            ImgView = itemView.findViewById(R.id.ivImage);
            delView = itemView.findViewById(R.id.ivDelete);
        }
    }

    static class ViewHolder_add extends RecyclerView.ViewHolder
    {
        ImageView ImgView;
        public ViewHolder_add(View itemView)
        {
            super(itemView);
            ImgView = itemView.findViewById(R.id.ivImage);
        }
    }



}
