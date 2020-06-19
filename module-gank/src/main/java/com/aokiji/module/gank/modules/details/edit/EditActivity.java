package com.aokiji.module.gank.modules.details.edit;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aokiji.module.gank.R;
import com.aokiji.module.gank.R2;
import com.aokiji.module.gank.model.PreviewImage;
import com.aokiji.module.gank.ui.adapter.PreviewAdapter;
import com.aokiji.module.gank.utils.RequestOptionsProvider;
import com.aokiji.mosby.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

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
        setupToolbar();

        Glide.with(this)
                .load(mUrl)
                .override(Target.SIZE_ORIGINAL)
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
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rvPreview.setLayoutManager(manager);
        mAdapter = new PreviewAdapter(this, mList);
        mAdapter.setOnItemClickListener((view, position) -> {
            Glide.with(EditActivity.this)
                    .load(mUrl)
                    .override(Target.SIZE_ORIGINAL)
                    .apply(RequestOptionsProvider.provide(mList.get(position).getPreviewType()))
                    .into(ivPreview);
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