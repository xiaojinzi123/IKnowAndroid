package com.iknow.module.main.module.article.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.main.ArticleDetailBean;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.base.support.HotObservableAnno;
import com.iknow.module.base.support.SingleObserverAdapter;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.impl.service.RxServiceManager;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

public class ArticleDetailViewModel extends BaseViewModel {

    private BehaviorSubject<ArticleDetailBean> articleDetailSubject = BehaviorSubject.create();

    public ArticleDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadArticle(String articleId) {

        Single<ArticleDetailBean> observable = RxServiceManager.with(DataSourceService.class)
                .flatMap(service -> service.articleDetail(articleId));

        subscribe(observable, new SingleObserverAdapter<>(item -> {
            articleDetailSubject.onNext(item);
        }));

    }

    @NonNull
    @HotObservableAnno
    public Observable<ArticleDetailBean> subscribeArticleDetail(){
        return articleDetailSubject;
    }

}
