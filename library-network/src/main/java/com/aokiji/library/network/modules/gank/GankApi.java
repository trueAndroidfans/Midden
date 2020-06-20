package com.aokiji.library.network.modules.gank;

import com.aokiji.library.network.Api;
import com.aokiji.library.network.entities.gank.BaseResult;
import com.aokiji.library.network.entities.gank.meizhi.Meizhi;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankApi extends Api {

    /**
     * 获取'妹纸'列表
     *
     * @return
     */
    @GET("category/Girl/type/Girl/page/{pageNum}/count/10")
    Observable<BaseResult<List<Meizhi>>> getMeizhiList(@Path("pageNum") int pageNum);
}
