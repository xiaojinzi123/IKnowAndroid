package com.iknow.module.base.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.iknow.module.base.support.CompleableObserverAdapter;
import com.iknow.module.base.support.ServiceException;
import com.iknow.module.base.support.SingleObserverAdapter;
import com.iknow.module.base.view.Tip;
import com.xiaojinzi.component.support.Utils;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

    // 一些 RxJava 订阅的通用方法

    protected <T> Disposable subscribe(@NonNull Single<T> observable,
                                       @NonNull SingleObserverAdapter<T> adapter) {
        Disposable disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(innerDisposable -> loadingSubject.onNext(true))
                .doFinally(() -> loadingSubject.onNext(false))
                .subscribe(
                        item -> adapter.getSuccessConsumer().accept(item),
                        error -> {
                            if (adapter.getErrorConsumer() == null) {
                                normalErrorSolve(error);
                            } else {
                                adapter.getErrorConsumer().accept(error);
                            }
                        }
                );
        disposables.add(disposable);
        return disposable;
    }

    protected <T> Disposable subscribe(@NonNull Completable observable,
                                       @NonNull CompleableObserverAdapter adapter) {
        Disposable disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(innerDisposable -> loadingSubject.onNext(true))
                .doFinally(() -> loadingSubject.onNext(false))
                .subscribe(
                        () -> adapter.getCompleteAction().run(),
                        error -> {
                            if (adapter.getErrorConsumer() == null) {
                                normalErrorSolve(error);
                            } else {
                                adapter.getErrorConsumer().accept(error);
                            }
                        }
                );
        disposables.add(disposable);
        return disposable;
    }

    private void normalErrorSolve(@NonNull Throwable error) {
        error = Utils.getRealThrowable(error);
        String message = error.getMessage();
        if (message == null) {
            message = "";
        }
        if (error instanceof ServiceException) {
            tipSubject.onNext(Tip.error(error.getMessage()));
        } else {
            tipSubject.onNext(Tip.error("未知异常"));
            error.printStackTrace();
        }
    }

}
