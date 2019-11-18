package com.iknow.module.datasource.network.apis;

import com.iknow.lib.beans.BannerBean;

import java.util.List;

import retrofit2.http.GET;

public interface IknowApi {

    @GET("banner/list/type/1")
    List<BannerBean> bannerList();

}
