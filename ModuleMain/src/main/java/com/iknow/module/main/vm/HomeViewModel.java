package com.iknow.module.main.vm;

import android.app.Application;
import androidx.annotation.NonNull;
import com.iknow.module.base.vm.BaseViewModel;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

public class HomeViewModel extends BaseViewModel {

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void testLoading() {
        Completable.complete()
                .delay(3, TimeUnit.SECONDS)
                .doOnEvent(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadingSubject.onNext(false);
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        loadingSubject.onNext(true);
                    }
                })
                .subscribe();
    }

}
