package com.iknow.module.datasource.network.apis;

import com.iknow.lib.beans.main.ArticleBean;
import com.iknow.lib.beans.main.ArticleDetailBean;
import com.iknow.lib.beans.main.BannerBean;
import com.iknow.lib.beans.main.CommonUrlBean;
import com.iknow.lib.beans.main.GirlBean;
import com.iknow.lib.beans.user.LoginBean;
import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.module.datasource.network.support.RemoveShell;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @GET("article/{articleId}")
    Single<ArticleDetailBean> articleDetail(
            @Path(value = "articleId") String articleId
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

    @RemoveShell
    @GET("image/girl/list")
    Single<List<GirlBean>> getGirlList(
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    @RemoveShell
    @GET("common/url/listAll")
    Single<List<CommonUrlBean>> getAllCommonUrl();

}
