package com.iknow.module.user.module.login.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.user.LoginBean;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.base.support.CompleableObserverAdapter;
import com.iknow.module.base.support.HotObservableAnno;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.impl.service.RxServiceManager;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * @Author：briskemen time：2019/11/23
 */
public class RegisterViewModel extends BaseViewModel {

    private BehaviorSubject<String> mPhoneNum = BehaviorSubject.createDefault("");
    private BehaviorSubject<String> mUserName = BehaviorSubject.createDefault("");

    /**
     * 注册按钮是否有用
     */
    private Subject<Boolean> mCommitEnable = BehaviorSubject.createDefault(false);

    /**
     * 成功的信号,一直都会发送 true 这个信号
     */
    private Subject<Boolean> mRegisterSuccess = BehaviorSubject.createDefault(false);

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 注册的流程
     */
    public void register() {

        Single<LoginBean> registerObservable = RxServiceManager.with(DataSourceService.class)
                .flatMap(service -> service.register(mPhoneNum.getValue(), mUserName.getValue()));

        Single<UserService> userServiceObservable = RxServiceManager.with(UserService.class);

        Completable observable = Single
                .zip(registerObservable, userServiceObservable, (loginBean, userService) -> userService.updateUserAndToken(loginBean.getToken(), loginBean.getUserInfo()))
                .flatMapCompletable(item -> item);

        subscribe(observable, new CompleableObserverAdapter(
                () -> mRegisterSuccess.onNext(true))
        );

    }

    public Observable<String> userName() {
        return mPhoneNum.distinctUntilChanged();
    }

    public Observable<String> password() {
        return mUserName.distinctUntilChanged();
    }

    public Observable<Boolean> canCommitObservable() {
        return mCommitEnable;
    }

    /**
     * 订阅是否成功的 Observable
     * 只会返回 true 的信号
     *
     * @return
     */
    @HotObservableAnno
    @NonNull
    public Observable<Boolean> registerSuccessObservable() {
        return mRegisterSuccess.filter(b -> b);
    }

    public void clearPhoneNum() {
        mPhoneNum.onNext("");
    }

    public void clearUserName() {
        mUserName.onNext("");
    }

    public void setPhoneNum(String s) {
        mPhoneNum.onNext(s);
    }

    public void setUserName(String s) {
        mUserName.onNext(s);
    }

}
