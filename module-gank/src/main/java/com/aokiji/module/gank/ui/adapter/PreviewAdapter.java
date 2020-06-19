package com.aokiji.module.gank.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aokiji.library.ui.listener.OnItemClickListener;
import com.aokiji.module.gank.R;
import com.aokiji.module.gank.model.PreviewImage;
import com.aokiji.module.gank.utils.RequestOptionsProvider;
import com.bumptech.glide.Glide;

import java.util.List;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.ViewHolder> {

    private Context mContext;
    private List<PreviewImage> mList;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public PreviewAdapter(Context context, List<PreviewImage> list) {
        this.mContext = context;
        this.mList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_edit, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreviewImage item = mList.get(position);
        Glide.with(mContext)
                .load(R.drawable.ic_preview)
                .apply(RequestOptionsProvider.provide(item.getPreviewType()))
                .into(holder.ivPreview);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
            });
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPreview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPreview = itemView.findViewById(R.id.iv_preview);
        }
    }

}
