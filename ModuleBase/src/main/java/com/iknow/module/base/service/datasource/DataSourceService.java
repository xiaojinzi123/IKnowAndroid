package com.iknow.module.base.service.datasource;

import com.iknow.lib.beans.BannerBean;

import java.util.List;

import io.reactivex.Single;

public interface DataSourceService {

    /**
     * 获取banner 的数据
     *
     * @return
     */
    Single<List<BannerBean>> bannerList();

}
