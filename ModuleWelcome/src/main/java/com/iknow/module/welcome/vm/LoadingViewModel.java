package com.iknow.module.welcome.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.iknow.module.base.vm.BaseViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class LoadingViewModel extends BaseViewModel {

    Subject<Long> restCountSubject = BehaviorSubject.create();

    Subject<Object> countdownSubject = BehaviorSubject.create();

    public LoadingViewModel(@NonNull Application application) {
        super(application);

        Disposable disposable = Observable
                .intervalRange(1, 3, 0, 1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long value) throws Exception {
                        restCountSubject.onNext(value);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        countdownSubject.onNext("");
                        countdownSubject.onComplete();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        countdownSubject.onNext("");
                        countdownSubject.onComplete();
                    }
                });

        disposables.add(disposable);

    }

    public Observable<Long> restCountObservable() {
        return restCountSubject;
    }

    public Completable countdownObservable() {
        return countdownSubject.ignoreElements();
    }

}
