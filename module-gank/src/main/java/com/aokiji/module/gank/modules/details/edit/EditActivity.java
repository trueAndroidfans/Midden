package com.aokiji.module.gank.modules.details.edit;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aokiji.library.base.utils.screen.ScreenUtil;
import com.aokiji.module.gank.R;
import com.aokiji.module.gank.R2;
import com.aokiji.module.gank.model.PreviewImage;
import com.aokiji.module.gank.ui.adapter.PreviewAdapter;
import com.aokiji.mosby.base.BaseActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

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

    private String mUrl;
    private String mDesc;

    private List<PreviewImage> mList = new ArrayList<>();
    private PreviewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        initData();

        initView();
    }


    private void initData() {
        mUrl = getIntent().getStringExtra("URL");
        mDesc = getIntent().getStringExtra("DESC");
        for (int i = 0; i < 12; i++) {
            PreviewImage image = new PreviewImage();
            image.setPreviewType(i);
            image.setUrl(mUrl);
            image.setDesc(mDesc);
            image.setChecked(false);
            mList.add(image);
        }
        mList.get(0).setChecked(true);
    }


    private void initView() {
        mScreenWidth = ScreenUtil.getScreenWidth(getApplicationContext());
        mScreenHeight = ScreenUtil.getScreenHeight(getApplicationContext());

        setupToolbar();

        Glide.with(this)
                .load(mUrl)
                .placeholder(R.color.color_e6)
                .error(R.color.color_e6)
                .into(ivPreview);

        setupRecyclerView();
    }


    private void setupToolbar() {
        toolbar.setTitle(R.string.title_edit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setupRecyclerView() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rvPreview.getLayoutParams();
        // RecyclerView 高度
        int awesomeHeight = Math.round((mScreenHeight - 50) / 5);
        // Item 宽度
        int awesomeWidth = Math.round(mScreenWidth / 5);
        params.height = awesomeHeight;
        rvPreview.requestLayout();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rvPreview.setLayoutManager(manager);
        mAdapter = new PreviewAdapter(this, mList);
        mAdapter.setAwesomeWidth(awesomeWidth);
        mAdapter.setOnItemClickListener((view, position) -> {
            for (PreviewImage item : mList) {
                item.setChecked(false);
            }
            mList.get(position).setChecked(true);
            mAdapter.notifyDataSetChanged();
        });
        rvPreview.setAdapter(mAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ActivityCompat.finishAfterTransition(EditActivity.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}