package com.denong.doluck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denong.doluck.R;

import java.util.List;

/**
 * Created by gs on 2017/12/26.
 */

public class HomeRcBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> contentList;

    private LayoutInflater inflater;

    public HomeRcBottomAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<String> contentList) {

        this.contentList = contentList;
        notifyDataSetChanged();
    }

    public OnHomeBottomItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnHomeBottomItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_home_rc_bottomadapter, parent, false);
        RecyclerView.ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        ViewHolder homeBottomHolder = (ViewHolder) holder;
        String url = contentList.get(position);
        if (position == 0){
            homeBottomHolder.tvTitle.setText("手机充值");
            homeBottomHolder.ivImage.setBackgroundResource(R.mipmap.recharge);
        }else if (position == 1){
            homeBottomHolder.tvTitle.setText("酒店");
            homeBottomHolder.ivImage.setBackgroundResource(R.mipmap.hotel);
        }else if (position == 2){
            homeBottomHolder.tvTitle.setText("商超");
            homeBottomHolder.ivImage.setBackgroundResource(R.mipmap.business_super);
        }else if (position == 3){
            homeBottomHolder.tvTitle.setText("电影院");
            homeBottomHolder.ivImage.setBackgroundResource(R.mipmap.cinema);
        }else {
            homeBottomHolder.tvTitle.setText("电影院");
            homeBottomHolder.ivImage.setBackgroundResource(R.mipmap.cinema);
        }

//        Glide.with(context.getApplicationContext())
//                .load(url)
//                .dontAnimate()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(homeBottomHolder.ivImage);

        onItemEventClick(homeBottomHolder);
    }

    @Override
    public int getItemCount() {
        return contentList == null ? 0 : contentList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_HomeRcBottomAdapter);

            ivImage = itemView.findViewById(R.id.iv_HomeRcBottomAdapter);

//            ViewGroup.LayoutParams para = ivImage.getLayoutParams();//获取drawerlayout的布局
//            para.height = width1 * 236 / 695;//修改宽度
////        para.height = height1;//修改高度
//            ivImage.setLayoutParams(para); //设置修改后的布局。
        }
    }

    private void onItemEventClick(RecyclerView.ViewHolder holder) {
        final int position = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnBottomItemClick(v, position);
            }
        });
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                mOnItemClickListener.OnItemLongClick(v, position);
//                return true;
//            }
//        });
    }
    public interface OnHomeBottomItemClickListener {
        void OnBottomItemClick(View view, int position);
    }
}

