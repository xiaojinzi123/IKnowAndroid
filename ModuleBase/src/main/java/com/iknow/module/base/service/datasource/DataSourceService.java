package com.iknow.module.base.service.datasource;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.ArticleBean;
import com.iknow.lib.beans.BannerBean;

import java.util.List;

import io.reactivex.Single;

public interface DataSourceService {

    /**
     * 获取banner 的数据
     */
    @NonNull
    Single<List<BannerBean>> bannerList();

    /**
     * 获取文章列表
     *
     * @param pageNumber 第几页
     * @param pageSize   每页的数量
     * @return
     */
    @NonNull
    Single<List<ArticleBean>> articleList(
            int pageNumber, int pageSize
    );

}
