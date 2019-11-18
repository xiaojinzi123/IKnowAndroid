package com.iknow.module.datasource.network.service;

import com.iknow.lib.beans.BannerBean;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.xiaojinzi.component.anno.ServiceAnno;

import java.util.List;

import io.reactivex.Single;

@ServiceAnno(DataSourceService.class)
public class DataSourceServiceImpl implements DataSourceService {

    @Override
    public Single<List<BannerBean>> bannerList() {
        return null;
    }

}
