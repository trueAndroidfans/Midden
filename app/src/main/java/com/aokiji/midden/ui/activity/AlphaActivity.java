package com.aokiji.midden.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.aokiji.midden.R;
import com.aokiji.mosby.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AlphaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha);

        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(time -> {
                    Intent intent = new Intent(AlphaActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
    }
}
