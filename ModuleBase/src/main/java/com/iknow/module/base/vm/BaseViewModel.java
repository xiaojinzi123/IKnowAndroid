package com.iknow.module.base.vm;

import android.app.Application;
import android.app.Service;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.iknow.lib.tools.ResourceUtil;
import com.iknow.module.base.R;
import com.iknow.module.base.support.CompleableObserverAdapter;
import com.iknow.module.base.support.ErrorUtil;
import com.iknow.module.base.support.HotObservableAnno;
import com.iknow.module.base.support.ServiceException;
import com.iknow.module.base.support.SingleObserverAdapter;
import com.iknow.module.base.view.BusinessError;
import com.iknow.module.base.view.Tip;
import com.xiaojinzi.component.support.Utils;

import java.net.ConnectException;

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

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * disposable 容器
     */
    protected CompositeDisposable disposables = new CompositeDisposable();

    /**
     * 表示一个初始化的状态.
     */
    @NonNull
    protected final Subject<Boolean> initSubject = BehaviorSubject.createDefault(false);

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
     * 业务错误
     * 实际是一个 {@link Subject}
     */
    @NonNull
    protected final Subject<BusinessError> businessErrorSubject = BehaviorSubject.create();

    /**
     * 返回的热信号
     *
     * @return 返回的热信号
     */
    @NonNull
    @HotObservableAnno
    public Observable<Boolean> subscribeInitObservable() {
        return initSubject;
    }

    /**
     * 返回的热信号
     *
     * @return 返回的热信号
     */
    @NonNull
    @HotObservableAnno
    public Observable<Boolean> subscribeLoadingObservable() {
        return loadingSubject;
    }

    /**
     * 返回的热信号
     *
     * @return 返回的热信号
     */
    @NonNull
    @HotObservableAnno
    public Observable<Tip> subscribeTipObservable() {
        return tipSubject;
    }

    @NonNull
    @HotObservableAnno
    public Observable<BusinessError> subscribeBusinessError() {
        return businessErrorSubject;
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

    protected final void normalErrorSolve(@NonNull Throwable error) {
        Throwable realError = null;
        String message = null;
        if ((realError = ErrorUtil.isCauseBy(error, ServiceException.class)) != null) {
            tipSubject.onNext(Tip.error(realError.getMessage()));
        } else if ((realError = ErrorUtil.isCauseBy(error, ConnectException.class)) != null) {
            tipSubject.onNext(Tip.error(ResourceUtil.getString(R.string.resource_network_connect_exception)));
        } else {
            message = error.getMessage();
            if (message == null) {
                message = "";
            }
            tipSubject.onNext(Tip.error(error.getClass().getName() + " 未知异常: " + message));
            error.printStackTrace();
        }
    }

}
