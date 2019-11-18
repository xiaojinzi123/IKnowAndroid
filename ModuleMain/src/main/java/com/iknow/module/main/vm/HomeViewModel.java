package com.iknow.module.main.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.BannerBean;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.base.util.RxUtil;
import com.iknow.module.base.view.Tip;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.impl.service.RxServiceManager;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeViewModel extends BaseViewModel {

    private Subject<List<BannerBean>> bannerSubject = BehaviorSubject.create();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        loadBanner();
    }

    public void loadBanner() {
        disposables.add(
                RxServiceManager.with(DataSourceService.class)
                        .flatMap(service -> service.bannerList())
                        .compose(singleTransformer())
                        .subscribe(
                                items -> bannerSubject.onNext(items),
                                error -> tipSubject.onNext(Tip.normal(error.getMessage()))
                        )
        );
    }

    public Subject<List<BannerBean>> getBannerSubject() {
        return bannerSubject;
    }
}
