package com.aokiji.module.gank.modules.details.edit;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aokiji.library.base.utils.screen.ScreenUtil;
import com.aokiji.module.gank.R;
import com.aokiji.module.gank.R2;
import com.aokiji.mosby.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends BaseActivity {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.iv_preview)
    ImageView ivPreview;
    @BindView(R2.id.rv_preview)
    RecyclerView rvPreview;

    private int mScreenWidth, mScreenHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        initView();
    }


    private void initView() {
        mScreenWidth = ScreenUtil.getScreenWidth(getApplicationContext());
        mScreenHeight = ScreenUtil.getScreenHeight(getApplicationContext());

        setupToolbar();
    }


    private void setupToolbar() {
        toolbar.setTitle(R.string.title_edit);
        setSupportActionBar(toolbar);
    }


    private void setupRecyclerView() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rvPreview.getLayoutParams();
        // RecyclerView 高度
        int awesomeHeight = Math.round(mScreenHeight / 5);
        // Item 宽度
        int awesomeWidth = Math.round(mScreenWidth / 5);
        params.height = awesomeHeight;
        rvPreview.requestLayout();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rvPreview.setLayoutManager(manager);

    }

}