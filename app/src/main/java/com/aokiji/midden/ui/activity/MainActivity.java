package com.aokiji.midden.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.aokiji.midden.App;
import com.aokiji.midden.R;
import com.aokiji.mosby.base.BaseActivity;
import com.aokiji.mosby.utils.ToastUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.aokiji.library.base.Settings.DEBUG;
import static com.aokiji.library.base.route.RouteHub.GANK_MAIN;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_gank)
    LinearLayout llGank;

    // 最后一次按下返回键的时间
    private long mPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initARouter();

        initLogger();

        initView();
    }


    private void initARouter() {
        if (DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(App.getInstance());
    }


    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }


    private void initView() {
        toolbar.setTitle(R.string.text_main);
        setSupportActionBar(toolbar);
    }


    @OnClick(R.id.ll_gank)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_gank:
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(llGank, llGank.getWidth() / 2, llGank.getHeight() / 2, 0, 0);
                ARouter.getInstance()
                        .build(GANK_MAIN)
                        .withOptionsCompat(compat)
                        .navigation();
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mPressedTime > 3000) {
            Toast.makeText(this, R.string.text_try_again, Toast.LENGTH_SHORT).show();
            mPressedTime = currentTime;
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }
    
}
