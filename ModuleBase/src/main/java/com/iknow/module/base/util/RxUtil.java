package com.iknow.module.base.util;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 有关一些 RxJava 的工具
 */
public class RxUtil {

    public static <T> Observable<T> toObservable(@NonNull final ObservableField<T> observableField) {

        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(final ObservableEmitter<T> emitter) throws Exception {
                if (emitter.isDisposed()) {
                    return;
                }
                final androidx.databinding.Observable.OnPropertyChangedCallback onPropertyChangedCallback = new androidx.databinding.Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(androidx.databinding.Observable sender, int propertyId) {
                        if (emitter.isDisposed()) {
                            return;
                        }
                        emitter.onNext(observableField.get());
                    }
                };
                observableField.addOnPropertyChangedCallback(onPropertyChangedCallback);
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        observableField.removeOnPropertyChangedCallback(onPropertyChangedCallback);
                    }
                });
            }
        });

    }

}
