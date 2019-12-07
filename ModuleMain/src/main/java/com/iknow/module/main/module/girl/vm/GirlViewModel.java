package com.iknow.module.main.module.girl.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.main.GirlBean;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.base.support.HotObservableAnno;
import com.iknow.module.base.support.SingleObserverAdapter;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.impl.service.RxServiceManager;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

public class GirlViewModel extends BaseViewModel {

    private BehaviorSubject<List<GirlBean>> girlSubject = BehaviorSubject.create();

    public GirlViewModel(@NonNull Application application) {
        super(application);
        Single<List<GirlBean>> observable = RxServiceManager.with(DataSourceService.class)
                .flatMap(service -> service.getGirlList(1, 100));
        subscribe(observable, new SingleObserverAdapter<>(result -> {
            girlSubject.onNext(result);
        }));
    }

    @NonNull
    @HotObservableAnno
    public BehaviorSubject<List<GirlBean>> subscribeGirlObservable() {
        return girlSubject;
    }
}
