package com.iknow.module.main.module.home.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.main.ArticleBean;
import com.iknow.lib.beans.main.BannerBean;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.base.support.HotObservableAnno;
import com.iknow.module.base.view.Tip;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.impl.service.RxServiceManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class HomeViewModel extends BaseViewModel {

    private Subject<List<BannerBean>> bannerSubject = BehaviorSubject.create();
    private Subject<List<ArticleBean>> articleSubject = BehaviorSubject.create();
    private Subject<Boolean> refreshSubject = BehaviorSubject.createDefault(false);

    public HomeViewModel(@NonNull Application application) {
        super(application);
        refresh();
    }

    public void refresh(){
        disposables.add(
                RxServiceManager.with(DataSourceService.class)
                        .flatMap(service -> service.articleList(1, 100))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .doOnEvent((item, error) -> {
                            refreshSubject.onNext(false);
                        })
                        .doOnSubscribe(
                                disposable -> refreshSubject.onNext(true)
                        )
                        .subscribe(
                                items -> articleSubject.onNext(items),
                                error -> normalErrorSolve(error)
                        )
        );
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

    @NonNull
    @HotObservableAnno("banner数据")
    public Observable<List<BannerBean>> getBannerSubject() {
        return bannerSubject;
    }

    @NonNull
    @HotObservableAnno("文章列表数据")
    public Observable<List<ArticleBean>> getArticleSubject() {
        return articleSubject;
    }

    @NonNull
    @HotObservableAnno("刷新状态")
    public Observable<Boolean> subscribeRefreshObservable() {
        return refreshSubject;
    }

}
