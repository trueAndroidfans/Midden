package com.aokiji.module.gank.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aokiji.library.network.models.gank.meizhi.Meizhi;
import com.aokiji.library.ui.listener.OnItemClickListener;
import com.aokiji.module.gank.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class MeizhiAdapter extends RecyclerView.Adapter<MeizhiAdapter.ViewHolder> {

    private Context mContext;
    private List<Meizhi> mList;
    private OnItemClickListener mOnItemClickListener;

    public MeizhiAdapter(Context context, List<Meizhi> list) {
        this.mContext = context;
        this.mList = list;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_item_meizhi, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meizhi meizhi = mList.get(position);
        Glide.with(mContext)
                .load(meizhi.getUrl())
                .override(Target.SIZE_ORIGINAL)
                .placeholder(R.color.color_f3)
                .error(R.color.color_f3)
                .into(holder.ivMeizhi);

        if (mOnItemClickListener != null) {
            holder.ivMeizhi.setOnClickListener(v -> mOnItemClickListener.onItemClick(holder.ivMeizhi, holder.getLayoutPosition()));
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivMeizhi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMeizhi = itemView.findViewById(R.id.iv_meizhi);
        }
    }

}
