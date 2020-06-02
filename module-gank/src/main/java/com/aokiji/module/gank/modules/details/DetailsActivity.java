package com.aokiji.module.gank.modules.details;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.aokiji.module.gank.R;
import com.aokiji.module.gank.R2;
import com.aokiji.module.gank.utils.RxGank;
import com.aokiji.mosby.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DetailsActivity extends BaseActivity {

    @BindView(R2.id.iv_meizhi)
    PhotoView ivMeizhi;
    @BindView(R2.id.iv_save)
    ImageView ivSave;
    @BindView(R2.id.iv_edit)
    ImageView ivEdit;

    private String mUrl;
    private String mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        initData();
    }


    private void initData() {
        mUrl = getIntent().getStringExtra("URL");
        // 清除所有换行符,空格
        mDesc = getIntent().getStringExtra("DESC").replaceAll("\n", "").replaceAll(" ", "");
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


    @OnClick(R2.id.iv_save)
    void save() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.text_tip)
                .setMessage(R.string.text_download)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                    RxGank.saveImage(DetailsActivity.this, mUrl, mDesc)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(path ->
                                            Toast.makeText(DetailsActivity.this, String.format("%s%s", getString(R.string.text_download_success), path), Toast.LENGTH_SHORT).show(),
                                    throwable ->
                                            Toast.makeText(DetailsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }

}
