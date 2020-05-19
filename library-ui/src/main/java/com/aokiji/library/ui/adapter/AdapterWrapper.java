package com.aokiji.library.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.aokiji.library.ui.R;


public class AdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter mAdapter;

    /**
     * ItemView 类型：0：头布局,1：内容布局,2：脚布局
     */
    private final int TYPE_HEADER = 0;
    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 2;

    /**
     * 列表加载状态：0：正在加载,1：本次加载完成,2：加载结束
     */
    public static final int STATE_LOADING = 0;
    public static final int STATE_LOADING_COMPLETE = 1;
    public static final int STATE_LOADING_END = 2;

    // 列表当前加载状态
    private int mLoadingState = STATE_LOADING_COMPLETE;

    private View mHeaderView;
    private int mHeaderViewCount = 0;

    public AdapterWrapper(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_HEADER && mHeaderView != null) {
            return new HeaderViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_footer, viewGroup, false);
            return new FooterViewHolder(view);
        } else {
            return mAdapter.onCreateViewHolder(viewGroup, viewType);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof HeaderViewHolder) {
            return;
        } else if (viewHolder instanceof FooterViewHolder) {
            FooterViewHolder footer = (FooterViewHolder) viewHolder;
            switch (mLoadingState) {
                case STATE_LOADING:
                    footer.llLoading.setVisibility(View.VISIBLE);
                    footer.rlLoadingEnd.setVisibility(View.GONE);
                    break;
                case STATE_LOADING_COMPLETE:
                    footer.llLoading.setVisibility(View.GONE);
                    footer.rlLoadingEnd.setVisibility(View.GONE);
                    break;
                case STATE_LOADING_END:
                    footer.llLoading.setVisibility(View.GONE);
                    footer.rlLoadingEnd.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        } else {
            mAdapter.onBindViewHolder(viewHolder, i - mHeaderViewCount);
        }
    }


    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + mHeaderViewCount + 1;
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        // 解决上拉加载 LayoutManager为 GridLayoutManager时 FootView不独占一行问题
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            ((GridLayoutManager) manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int i) {
                    return getItemViewType(i) == TYPE_FOOTER ? ((GridLayoutManager) manager).getSpanCount() : 1;
                }
            });
        }
    }


    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        // 解决上拉加载 LayoutManager为 StaggeredGridLayoutManager FootView不独占一行问题
        if (getItemViewType(holder.getLayoutPosition()) == TYPE_FOOTER) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) params).setFullSpan(true);
            }
        }
    }

    public void addHeaderView(View view) {
        this.mHeaderView = view;
        mHeaderViewCount = mHeaderViewCount + 1;
        notifyItemChanged(0);
    }


    public void setLoadingState(int state) {
        this.mLoadingState = state;
        notifyItemChanged(getItemCount() - 1);
    }


    public int getLoadingState() {
        return mLoadingState;
    }


    public boolean canLoad() {
        boolean canLoad = true;
        if (mLoadingState == STATE_LOADING) {
            canLoad = false;
        }
        if (mLoadingState == STATE_LOADING_END) {
            canLoad = false;
        }

        return canLoad;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llLoading;
        private RelativeLayout rlLoadingEnd;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            llLoading = itemView.findViewById(R.id.ll_loading);
            rlLoadingEnd = itemView.findViewById(R.id.rl_loading_end);
        }
    }

}
