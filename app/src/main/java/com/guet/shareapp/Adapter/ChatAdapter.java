package com.guet.shareapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.guet.shareapp.Entity.xx;
import com.guet.shareapp.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {//适配器
    private List<xx> list;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView zuoimg, youimg;
        TextView zuotext, youtext;
        ViewGroup zuolin, youlin;

        ViewHolder(View itemView) {
            super(itemView);
            zuolin = itemView.findViewById(R.id.zuo);
            zuoimg = itemView.findViewById(R.id.zuoimg);
            zuotext = itemView.findViewById(R.id.zuotext);
            youlin = itemView.findViewById(R.id.you);
            youimg = itemView.findViewById(R.id.youimg);
            youtext = itemView.findViewById(R.id.youtext);
        }
    }

    public ChatAdapter(List l) {
        list = l;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.xx, parent, false);
        ViewHolder h = new ViewHolder(v);
//        Log.d("MainActivity", "onCreate");
        return h;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//滑动RecyclerView出发的事件
        xx x = list.get(position);
        if (x.zuo) {//判断该信息该信息是显示在左边还是右边，如果要在左边显示则把右边的部分隐藏
            holder.zuolin.setVisibility(View.VISIBLE);
            holder.youlin.setVisibility(View.GONE);//把右边的隐藏
            holder.zuoimg.setImageResource(x.img);
            holder.zuotext.setText(x.text);
        }else{
            holder.youlin.setVisibility(View.VISIBLE);
            holder.zuolin.setVisibility(View.GONE);//把左边的隐藏
            holder.youimg.setImageResource(x.img);
            holder.youtext.setText(x.text);
        }
        Log.d("MainActivity", "onBind");
    }

    @Override
    public int getItemCount() {//这里要重写一下 不然不会显示任何信息
        return list.size();
    }
}


