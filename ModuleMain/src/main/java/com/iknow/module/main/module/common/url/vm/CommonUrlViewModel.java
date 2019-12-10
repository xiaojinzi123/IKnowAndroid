package com.iknow.module.main.module.common.url.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.main.CommonUrlBean;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.base.support.HotObservableAnno;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.impl.service.RxServiceManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class CommonUrlViewModel extends BaseViewModel {

    private BehaviorSubject<List<CommonUrlBean>> commonUrlSubject = BehaviorSubject.create();

    public CommonUrlViewModel(@NonNull Application application) {
        super(application);
        refresh();
    }

    public void refresh() {

        disposables.add(RxServiceManager.with(DataSourceService.class)
                .flatMap(item -> item.getAllCommonUrl())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        items -> commonUrlSubject.onNext(items),
                        error -> normalErrorSolve(error)
                )
        );

    }

    @NonNull
    @HotObservableAnno
    public Observable<List<CommonUrlBean>> subscribeCommonUrlObservable(){
        return commonUrlSubject;
    }

}
