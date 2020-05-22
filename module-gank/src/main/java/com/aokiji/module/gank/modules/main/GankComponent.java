package com.aokiji.module.gank.modules.main;

import com.aokiji.library.network.modules.gank.GankNetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = GankNetworkModule.class)
public interface GankComponent {
    void inject(GankMainActivity activity);
}
