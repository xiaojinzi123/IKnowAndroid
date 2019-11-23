package com.iknow.module.datasource.network.apis;

import com.iknow.lib.beans.ArticleBean;
import com.iknow.lib.beans.BannerBean;
import com.iknow.lib.beans.LoginBean;
import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.module.datasource.network.support.RemoveShell;
import io.reactivex.Single;
import retrofit2.http.*;

import java.util.List;

/**
 * 项目的接口文件
 */
public interface IknowApi {

    @RemoveShell
    @GET("banner/list/type/1")
    Single<List<BannerBean>> bannerList();

    @RemoveShell
    @GET("article/list")
    Single<List<ArticleBean>> articleList(
            @Query(value = "pageNumber") int pageNumber,
            @Query("pageSize") int pageSize
    );

    @RemoveShell
    @FormUrlEncoded
    @POST("user/login")
    Single<LoginBean> login(
            @Field("userName") String userName,
            @Field("password") String password
    );

    @RemoveShell
    @FormUrlEncoded
    @POST("user/register")
    Single<LoginBean> register(
            @Field("phone") String phone,
            @Field("password") String password
    );

    @RemoveShell
    @GET("user/fullInfo/{id}")
    Single<UserInfoBean> getUser(@Path("id") Integer userId);

}
