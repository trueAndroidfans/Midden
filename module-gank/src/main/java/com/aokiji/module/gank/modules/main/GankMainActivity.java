package com.aokiji.module.gank.modules.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aokiji.module.gank.R;

import static com.aokiji.library.base.route.RouteHub.GANK_MAIN;

@Route(path = GANK_MAIN)
public class GankMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_main);
    }
}
