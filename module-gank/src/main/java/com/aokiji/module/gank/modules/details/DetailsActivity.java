package com.aokiji.module.gank.modules.details;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.aokiji.module.gank.R;
import com.aokiji.module.gank.R2;
import com.aokiji.module.gank.modules.details.edit.EditActivity;
import com.aokiji.module.gank.utils.RxGank;
import com.aokiji.mosby.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DetailsActivity extends BaseActivity {

    @BindView(R2.id.iv_meizhi)
    PhotoView ivMeizhi;
    @BindView(R2.id.iv_save)
    ImageView ivSave;
    @BindView(R2.id.iv_edit)
    ImageView ivEdit;

    private String mUrl;
    private String mDesc;

    private CompositeDisposable mCompositeDisposable;

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
        // 清除所有换行符,空格
        mDesc = getIntent().getStringExtra("DESC").replaceAll("\n", "").replaceAll(" ", "");
        mCompositeDisposable = new CompositeDisposable();
    }


    @OnClick(R2.id.iv_meizhi)
    void exit() {
        ActivityCompat.finishAfterTransition(this);
    }


    @OnClick(R2.id.iv_save)
    void save() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.text_tip)
                .setMessage(R.string.text_download)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                    mDesc = TextUtils.isEmpty(mDesc) ? String.valueOf(System.currentTimeMillis()) : mDesc;
                    Disposable disposable = RxGank.saveImage(DetailsActivity.this, mUrl, mDesc)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(path ->
                                            Toast.makeText(DetailsActivity.this, String.format("%s%s", getString(R.string.text_download_success), path), Toast.LENGTH_SHORT).show(),
                                    throwable ->
                                            Toast.makeText(DetailsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
                    if (mCompositeDisposable != null) {
                        mCompositeDisposable.add(disposable);
                    }
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                }).show();

    }


    @OnClick(R2.id.iv_edit)
    void edit() {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, ivMeizhi, getString(R.string.tn_preview));
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("URL", mUrl);
        intent.putExtra("DESC", mDesc);
        ActivityCompat.startActivity(this, intent, compat.toBundle());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

}
