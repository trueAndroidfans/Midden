package com.aokiji.library.network.modules.gank;

import android.content.Context;

import com.aokiji.library.network.BackPack;
import com.aokiji.library.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GankNetworkModule extends NetworkModule {

    private Context mContext;

    public GankNetworkModule(Context context) {
        this.mContext = context;
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }


    @Singleton
    @Provides
    public GankApi provideGankApi() {
        return generateRetrofit(mContext, BackPack.Gank.BASE_URL).create(GankApi.class);
    }

}
