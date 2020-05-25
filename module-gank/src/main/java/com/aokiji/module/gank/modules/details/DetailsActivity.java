package com.aokiji.module.gank.modules.details;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.aokiji.module.gank.R;
import com.aokiji.module.gank.R2;
import com.aokiji.mosby.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends BaseActivity {

    @BindView(R2.id.iv_meizhi)
    PhotoView ivMeizhi;
    @BindView(R2.id.iv_save)
    ImageView ivSave;
    @BindView(R2.id.iv_edit)
    ImageView ivEdit;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        initData();
    }


    private void initData() {
        mUrl = getIntent().getStringExtra("URL");
        if (!TextUtils.isEmpty(mUrl)) {
            Glide.with(this)
                    .load(mUrl)
                    .placeholder(R.color.color_f3)
                    .error(R.color.color_f3)
                    .into(ivMeizhi);
        } else {
            finish();
        }
    }


    @OnClick(R2.id.iv_meizhi)
    void exit() {
        finish();
    }

}
