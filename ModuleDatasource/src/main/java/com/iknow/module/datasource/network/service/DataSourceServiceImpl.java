package com.iknow.module.datasource.network.service;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.ArticleBean;
import com.iknow.lib.beans.BannerBean;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.datasource.network.ApiManager;
import com.xiaojinzi.component.anno.ServiceAnno;

import java.util.List;

import io.reactivex.Single;

@ServiceAnno(DataSourceService.class)
public class DataSourceServiceImpl implements DataSourceService {

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

}
