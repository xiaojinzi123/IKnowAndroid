package com.iknow.module.datasource.network.service;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.main.ArticleBean;
import com.iknow.lib.beans.main.ArticleDetailBean;
import com.iknow.lib.beans.main.BannerBean;
import com.iknow.lib.beans.main.GirlBean;
import com.iknow.lib.beans.user.LoginBean;
import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.datasource.network.ApiManager;
import com.xiaojinzi.component.anno.ServiceAnno;

import java.util.List;

import io.reactivex.Single;

@ServiceAnno(DataSourceService.class)
public class DataSourceServiceImpl implements DataSourceService {

    @NonNull
    @Override
    public Single<UserInfoBean> getUser(@NonNull Integer userId) {
        return ApiManager.getInstance()
                .getIknowApi()
                .getUser(userId);
    }

    @NonNull
    @Override
    public Single<LoginBean> login(@NonNull String userName, @NonNull String password) {
        return ApiManager.getInstance()
                .getIknowApi()
                .login(userName, password);
    }

    @Override
    public Single<LoginBean> register(@NonNull String phoneNum, @NonNull String userName) {
        return ApiManager.getInstance()
                .getIknowApi()
                .register(phoneNum, userName);
    }

    @Override
    public Single<List<BannerBean>> bannerList() {
        return ApiManager.getInstance()
                .getIknowApi()
                .bannerList();
    }

    @NonNull
    @Override
    public Single<List<ArticleBean>> articleList(int pageNumber, int pageSize) {
        return ApiManager.getInstance()
                .getIknowApi()
                .articleList(pageNumber, pageSize);
    }

    @NonNull
    @Override
    public Single<ArticleDetailBean> articleDetail(String articleId) {
        return ApiManager.getInstance()
                .getIknowApi()
                .articleDetail(articleId);
    }

    @NonNull
    @Override
    public Single<List<GirlBean>> getGirlList(int page, int pageSize) {
        return ApiManager.getInstance()
                .getIknowApi()
                .getGirlList(page, pageSize);
    }
}
