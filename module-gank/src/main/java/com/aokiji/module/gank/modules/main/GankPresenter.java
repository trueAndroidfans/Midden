package com.aokiji.module.gank.modules.main;

import android.content.Context;

import com.aokiji.library.network.BackPack;
import com.aokiji.library.network.modules.gank.GankApi;
import com.aokiji.library.network.monitors.NoNetworkException;
import com.aokiji.module.gank.R;
import com.aokiji.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GankPresenter extends MvpBasePresenter<GankView> {

    private Context mContext;
    private GankApi mApi;

    private CompositeDisposable mCompositeDisposable;

    @Inject
    public GankPresenter(Context context, GankApi api) {
        this.mContext = context;
        this.mApi = api;
        mCompositeDisposable = new CompositeDisposable();
    }


    public void getMeizhiList(int pageNum, boolean pullToRefresh) {
        ifViewAttached(gankView -> {
            gankView.showLoading(pullToRefresh);
            Disposable disposable = mApi.getMeizhiList(pageNum)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data -> {
                        if (data.getStatus() == BackPack.Gank.RESULT_SUCCESS) {
                            gankView.setData(data.getData());
                            gankView.showContent();
                        } else {
                            gankView.showError(new Throwable(mContext.getString(R.string.text_error)), pullToRefresh);
                        }
                    }, throwable -> {
                        if (throwable instanceof NoNetworkException) {
                            gankView.showError(new Throwable(mContext.getString(R.string.text_no_network)), pullToRefresh);
                        }
                    });
            if (mCompositeDisposable != null) {
                mCompositeDisposable.add(disposable);
            }
        });
    }


    @Override
    public void destroy() {
        super.destroy();
        mContext = null;
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;
        }
    }

}
