package com.aokiji.library.network;

import android.content.Context;

import com.aokiji.library.network.interceptor.HeadInterceptor;
import com.aokiji.library.network.interceptor.LoggingInterceptor;
import com.aokiji.library.network.monitor.LiveNetworkMonitor;
import com.aokiji.library.network.monitor.NetworkMonitor;
import com.aokiji.library.network.monitor.NoNetworkException;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {

    protected Retrofit generateRetrofit(Context context, String baseUrl) {
        NetworkMonitor monitor = new LiveNetworkMonitor(context);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HeadInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(chain -> {
                    if (monitor.isConnected()) {
                        return chain.proceed(chain.request());
                    } else {
                        throw new NoNetworkException();
                    }
                })
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

}
