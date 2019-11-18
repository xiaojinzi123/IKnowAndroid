package com.iknow.module.datasource.network.apis;

import com.iknow.lib.beans.BannerBean;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface IknowApi {

    @GET("banner/list/type/1")
    Single<List<BannerBean>> bannerList();

}
