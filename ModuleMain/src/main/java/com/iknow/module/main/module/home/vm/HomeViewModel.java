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
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class HomeViewModel extends BaseViewModel {

    private Subject<List<BannerBean>> bannerSubject = BehaviorSubject.create();
    private Subject<List<ArticleBean>> articleSubject = BehaviorSubject.create();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        loadArticle();
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

    public void loadArticle() {
        disposables.add(
                RxServiceManager.with(DataSourceService.class)
                        .flatMap(service -> service.articleList(1, 100))
                        .compose(singleTransformer())
                        .subscribe(
                                items -> articleSubject.onNext(items),
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

}
