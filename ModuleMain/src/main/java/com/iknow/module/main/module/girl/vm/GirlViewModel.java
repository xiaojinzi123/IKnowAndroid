package com.iknow.module.main.module.girl.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.main.GirlBean;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.base.support.HotObservableAnno;
import com.iknow.module.base.support.SingleObserverAdapter;
import com.iknow.module.base.view.Tip;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.impl.service.RxServiceManager;
import com.xiaojinzi.component.impl.service.ServiceManager;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

public class GirlViewModel extends BaseViewModel {

    private BehaviorSubject<List<GirlBean>> girlSubject = BehaviorSubject.create();

    public GirlViewModel(@NonNull Application application) {
        super(application);
        DataSourceService dataSourceService = ServiceManager.get(DataSourceService.class);
        Single<List<GirlBean>> observable = RxServiceManager.with(DataSourceService.class)
                // .doOnSuccess(item -> )
                .flatMap(service -> {
                    tipSubject.onNext(Tip.normal(dataSourceService + " service1: " + service.getClass().getName()));
                    try {
                        Single<List<GirlBean>> girlList = service.getGirlList(1, 100);
                        tipSubject.onNext(Tip.normal("service2: "));
                        // tipSubject.onNext(Tip.normal("service2: " + girlList));
                    } catch (Exception e) {
                        tipSubject.onNext(Tip.normal("serviceError: " + e.getMessage()));
                    }
                    return service.getGirlList(1, 100);
                })
                .doOnSuccess( item -> {
                    tipSubject.onNext(Tip.normal("item1: " + item.getClass().getName()));
                });
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
