package com.guet.shareapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.guet.shareapp.Entity.ImageEntity;
import com.guet.shareapp.R;
import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder>{

    private List<ImageEntity> discoverList;
    private Context context;
//    BottomSheetDialog dialog;


    public DiscoverAdapter(Context context, List<ImageEntity>List)
    {
        this.discoverList = List;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverAdapter.ViewHolder holder, int position)
    {
        ImageEntity entity = discoverList.get(position);

        //展示的图片
        Glide.with(context)
                .load(entity.getThumbnailUrl())
//                .load(entity.getDisplayImgUrl())
                .error(R.drawable.ic_load)
                .fitCenter()
                .into(holder.discoverDisplayImgView);

        //头像
        Glide.with(context)
                .load(entity.getAuthorProfileImgUrl())
                .centerCrop()
                //太耗时
//                .placeholder(R.drawable.defalut_profile)
                .error(R.drawable.head)
                .into(holder.discoverAuthorProfileImgView);
        holder.discoverImgNameTextView.setText(entity.getDisplayImgName());
        holder.discoverAuthorNameTextView.setText(entity.getAuthorName());
        holder.discoverStarNumTextView.setText(String.valueOf(entity.getStarNum()));

        if (entity.isStared())  //已经点赞
        {
            holder.discoverLikeImgView.setImageResource(R.drawable.ic_unlike);
        }
        else //未点赞
        {
            holder.discoverLikeImgView.setImageResource(R.drawable.ic_like);
        }
//        holder.squareDownloadNumTextView.setText(String.valueOf(entity.getDownloadNum()));
    }

    @NonNull
    @Override
    public DiscoverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discover, parent, false);
        final ViewHolder holder = new ViewHolder(view);



//        holder.squareDisplayImgView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                int position = holder.getAdapterPosition();
//                ImageEntity entity = squareList.get(position);
//                Intent intent = new Intent(context, ImageDetailActivity.class);
//                intent.putExtra("ImageEntity", entity);
//                context.startActivity(intent);
//            }
//        });

//        holder.squareLikeImgView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                int position = holder.getAdapterPosition();
//                ImageEntity entity = squareList.get(position);
//                if (entity.isStared())  //已经点赞，则取消
//                {
//                    entity.setStared(false);
//                    entity.setStarNum(entity.getStarNum() - 1);
//                    holder.squareStarNumTextView.setText(String.valueOf(entity.getStarNum()));
//                    holder.squareLikeImgView.setImageResource(R.drawable.ic_like);
//                }
//                else    //未点赞，则加上
//                {
//                    entity.setStared(true);
//                    entity.setStarNum(entity.getStarNum() + 1);
//                    holder.squareStarNumTextView.setText(String.valueOf(entity.getStarNum()));
//                    holder.squareLikeImgView.setImageResource(R.drawable.ic_unlike);
//                }
//
//                //改变云端点赞数量
//                Map<String, String> map = new HashMap<>();
//                map.put("pictureId", entity.getImgID());
//                String json = new Gson().toJson(map);
//                try
//                {
//                    OkhttpUtils.post("picture/star", json, new Callback()
//                    {
//                        @Override
//                        public void onFailure(@NotNull Call call, @NotNull IOException e)
//                        {
//                        }
//
//                        @Override
//                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
//                        {
//                        }
//                    });
//                } catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        });

//        holder.squareDownloadImgView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                int position = holder.getAdapterPosition();
//                ImageEntity entity = squareList.get(position);
//
//                holder.squareDownloadImgView.setClickable(false);
////                holder.squareDownloadImgView.setImageResource(R.drawable.ic_downloaded);
//                entity.setDownloadNum(entity.getDownloadNum() + 1);
//                holder.squareDownloadNumTextView.setText(String.valueOf(entity.getDownloadNum()));
//
//                //更新云端数据：此图片下载数加一
//                Map<String, String> map = new HashMap<>();
//                map.put("pictureId", entity.getImgID());
//                String json = new Gson().toJson(map);
//                try
//                {
//                    OkhttpUtils.post("picture/download_count", json, new Callback()
//                    {
//                        @Override
//                        public void onFailure(@NotNull Call call, @NotNull IOException e)
//                        {
//                        }
//
//                        @Override
//                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
//                        {
//                        }
//                    });
//                } catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//
//                //下载
//                new Thread(() -> {
//                    try
//                    {
//                        FutureTarget<File> target = Glide.with(context)
//                                .asFile()
//                                .load(entity.getDisplayImgUrl())
//                                .submit();
//                        final File imageFile = target.get();
//                        String fileName = entity.getDisplayImgName() + "_" + entity.getDisplayImgUrl().substring(entity.getDisplayImgUrl().lastIndexOf('/')+1);
//                        boolean saveSuccess = MyFileUtils.saveFile(imageFile, fileName);
//                        //不能在子线程用toast
////                        if (saveSuccess)
////                        {
////                            Log.e(TAG, "Save：图片保存成功");
////                            Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
////                        }
////                        else
////                        {
////                            Log.e(TAG, "Save：图片保存失败");
////                            Toast.makeText(context, "图片保存失败", Toast.LENGTH_SHORT).show();
////                        }
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }).start();
//                Toast.makeText(context, "图片正在保存~", Toast.LENGTH_SHORT).show();
//            }
//
//        });

        holder.discoverAuthorProfileImgView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                ImageEntity entity = discoverList.get(position);
                Toast.makeText(context, entity.getAuthorName(), Toast.LENGTH_SHORT).show();
            }
        });


        return holder;
    }

    @Override
    public int getItemCount()
    {
        return discoverList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;

        ImageView discoverDisplayImgView;
        TextView discoverImgNameTextView;
        TextView discoverAuthorNameTextView;
        ImageView discoverAuthorProfileImgView;
        TextView discoverStarNumTextView;
        ImageView discoverLikeImgView;
        TextView  squareDownloadNumTextView;
        ImageView squareDownloadImgView;
//        ImageView squareCommentImgView;
//        TextView  squareCommentNumTextView;
//        ImageView squareMoreImgView;

        public ViewHolder(View v)
        {
            super(v);
            itemView = v;
            discoverDisplayImgView = v.findViewById(R.id.discover_img);
            discoverImgNameTextView = v.findViewById(R.id.tv_img_name);
            discoverAuthorNameTextView = v.findViewById(R.id.tv_author_name);
            discoverAuthorProfileImgView = v.findViewById(R.id.iv_author);
            discoverStarNumTextView = v.findViewById(R.id.tv_like_num);
            discoverLikeImgView = v.findViewById(R.id.iv_like);
        }
    }

}
