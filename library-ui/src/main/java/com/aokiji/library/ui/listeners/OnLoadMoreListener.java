package com.aokiji.library.ui.listeners;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

abstract public class OnLoadMoreListener extends RecyclerView.OnScrollListener {

    private boolean isSlidingUpward;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (isSlidingUpward && getMaxElement(manager.findLastCompletelyVisibleItemPositions(new int[manager.getSpanCount()])) == manager.getItemCount() - 1) {
                onLoadMore();
            }
        }
    }


    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        isSlidingUpward = dy > 0;
    }


    private int getMaxElement(int[] array) {
        int size = array.length;
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (array[i] > maxValue)
                maxValue = array[i];
        }

        return maxValue;
    }

    abstract public void onLoadMore();

}
