package com.aokiji.module.gank.modules.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aokiji.library.network.models.gank.meizhi.Meizhi;
import com.aokiji.library.network.modules.gank.GankNetworkModule;
import com.aokiji.library.ui.adapter.AdapterWrapper;
import com.aokiji.library.ui.listener.OnLoadMoreListener;
import com.aokiji.module.gank.R;
import com.aokiji.module.gank.R2;
import com.aokiji.module.gank.modules.details.DetailsActivity;
import com.aokiji.module.gank.ui.adapter.MeizhiAdapter;
import com.aokiji.mosby.mvp.lce.MvpLceActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aokiji.library.base.route.RouteHub.GANK_MAIN;

@Route(path = GANK_MAIN)
public class GankMainActivity extends MvpLceActivity<SwipeRefreshLayout, List<Meizhi>, GankView, GankPresenter> implements GankView {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.rv_girl)
    RecyclerView rvGirl;
    @BindView(R2.id.contentView)
    SwipeRefreshLayout refreshLayout;

    @Inject
    GankPresenter mPresenter;

    private int pageNum = 1;
    private List<Meizhi> mList = new ArrayList<>();
    private MeizhiAdapter mAdapter;
    private AdapterWrapper mAdapterWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_main);
        ButterKnife.bind(this);

        initView();

        loadData(false);
    }


    @Override
    protected void initDependencies() {
        DaggerGankComponent.builder().gankNetworkModule(new GankNetworkModule(this)).build().inject(this);
    }


    private void initView() {
        setupToolbar();
        setupRefreshLayout();
        setupRecyclerView();
    }


    private void setupToolbar() {
        toolbar.setTitle(R.string.title_main);
        setSupportActionBar(toolbar);
    }


    private void setupRefreshLayout() {
        refreshLayout.setColorSchemeResources(R.color.color_26a69a, android.R.color.white);
        refreshLayout.setOnRefreshListener(() -> {
            pageNum = 1;
            loadData(true);
        });
    }


    private void setupRecyclerView() {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvGirl.setLayoutManager(manager);
        mAdapter = new MeizhiAdapter(this, mList);
        mAdapter.setOnItemClickListener((view, position) -> {
            Meizhi meizhi = mList.get(position);
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, getString(R.string.tn_meizhi));
            Intent intent = new Intent(GankMainActivity.this, DetailsActivity.class);
            intent.putExtra("URL", meizhi.getUrl());
            ActivityCompat.startActivity(GankMainActivity.this, intent, compat.toBundle());
        });
        mAdapterWrapper = new AdapterWrapper(mAdapter);
        rvGirl.setAdapter(mAdapterWrapper);
        rvGirl.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mAdapterWrapper.canLoad()) {
                    mAdapterWrapper.setLoadingState(AdapterWrapper.STATE_LOADING);
                    pageNum = pageNum + 1;
                    loadData(true);
                }
            }
        });
    }


    @NonNull
    @Override
    public GankPresenter createPresenter() {
        return mPresenter;
    }


    @Override
    protected String getErrorMessage(Throwable throwable, boolean b) {
        return throwable.getMessage();
    }


    @Override
    public void setData(List<Meizhi> data) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        if (data != null && !data.isEmpty()) {
            if (data.size() < 10) {
                mAdapterWrapper.setLoadingState(AdapterWrapper.STATE_LOADING_END);
            } else {
                mAdapterWrapper.setLoadingState(AdapterWrapper.STATE_LOADING_COMPLETE);
            }
            if (pageNum == 1) {
                mList.clear();
                mList.addAll(data);
                mAdapterWrapper.notifyDataSetChanged();
            } else {
                mList.addAll(data);
                mAdapterWrapper.notifyItemRangeChanged(mList.size() - data.size(), data.size());
            }
        } else {
            if (pageNum == 1) {
                showError(new Throwable(getString(R.string.text_no_data)), false);
            } else {
                mAdapterWrapper.setLoadingState(AdapterWrapper.STATE_LOADING_END);
            }
        }
    }


    @Override
    public void loadData(boolean b) {
        mPresenter.getMeizhiList(pageNum, b);
    }

}
