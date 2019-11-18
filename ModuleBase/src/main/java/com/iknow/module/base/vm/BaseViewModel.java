package com.iknow.module.base.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.iknow.module.base.view.Tip;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * The Base ViewModel
 *
 * @author xiaojinzi
 */
public class BaseViewModel extends AndroidViewModel {

    private final SingleTransformer singleTransformer = new SingleTransformer() {
        @Override
        public SingleSource apply(Single upstream) {
            return upstream
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnEvent((item, error) -> {
                        loadingSubject.onNext(false);
                    })
                    .doOnSubscribe(
                            disposable -> loadingSubject.onNext(true)
                    );
        }
    };

    public <T> SingleTransformer<T, T> singleTransformer() {
        return singleTransformer;
    }

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * disposable 容器
     */
    protected CompositeDisposable disposables = new CompositeDisposable();

    /**
     * 表示 loading 状态,false 表示关闭 loading 弹框,true 表示打开
     * 实际是一个 {@link Subject}
     */
    @NonNull
    protected final Subject<Boolean> loadingSubject = BehaviorSubject.createDefault(false);

    /**
     * 提示信息
     * 实际是一个 {@link Subject}
     */
    @NonNull
    protected final Subject<Tip> tipSubject = BehaviorSubject.create();

    /**
     * 返回的热信号
     *
     * @return 返回的热信号
     */
    @NonNull
    public Observable<Boolean> loadingObservable() {
        return loadingSubject;
    }

    /**
     * 返回的热信号
     *
     * @return 返回的热信号
     */
    @NonNull
    public Observable<Tip> tipObservable() {
        return tipSubject;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose();
    }

}
