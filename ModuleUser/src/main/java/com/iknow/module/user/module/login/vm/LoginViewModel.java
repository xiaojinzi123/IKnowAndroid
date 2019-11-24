package com.iknow.module.user.module.login.vm;

import android.app.Application;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.iknow.lib.beans.LoginBean;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.base.support.CompleableObserverAdapter;
import com.iknow.module.base.view.Tip;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.impl.service.RxServiceManager;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;


/**
 * desc:
 *
 * @author: 32052
 * time: 2019/4/25
 */
public class LoginViewModel extends BaseViewModel {

    private BehaviorSubject<String> mUserName = BehaviorSubject.createDefault("");
    private BehaviorSubject<String> mPassword = BehaviorSubject.createDefault("");

    /**
     * 账号清楚图标是否显示
     */
    private Subject<Boolean> mUserNameVisible = BehaviorSubject.createDefault(false);
    private Subject<Boolean> mPasswordVisible = BehaviorSubject.createDefault(false);

    /**
     * 登录按钮是否有用
     */
    private Subject<Boolean> mCommitEnable = BehaviorSubject.createDefault(false);

    /**
     * 成功的信号,一直都会发送 true 这个信号
     */
    private Subject<Boolean> mLoginSuccess = BehaviorSubject.createDefault(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);

        disposables.add(
                mUserName.map(s -> !TextUtils.isEmpty(s)).subscribe(b -> mUserNameVisible.onNext(b))
        );

        disposables.add(
                mPassword.map(s -> !TextUtils.isEmpty(s)).subscribe(b -> mPasswordVisible.onNext(b))
        );

        disposables.add(
                Observable
                        .combineLatest(mUserName, mPassword, (s1, s2) -> !TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2))
                        .subscribe(b -> {
                            tipSubject.onNext(Tip.error(""));
                            mCommitEnable.onNext(b);
                        })
        );

    }

    /**
     * 登录的流程
     */
    public void login() {

        Single<LoginBean> loginObservable = RxServiceManager.with(DataSourceService.class)
                .flatMap(service -> service.login(mUserName.getValue(), mPassword.getValue()));

        Single<UserService> userServiceObservable = RxServiceManager.with(UserService.class);

        Completable observable = Single
                .zip(loginObservable, userServiceObservable, (loginBean, userService) -> userService.updateUserAndToken(loginBean.getToken(), loginBean.getUserInfo()))
                .flatMapCompletable(item -> item);

        subscribe(observable, new CompleableObserverAdapter(
                () -> mLoginSuccess.onNext(true))
        );

    }

    public Observable<String> userName() {
        return mUserName.distinctUntilChanged();
    }

    public Observable<String> password() {
        return mPassword.distinctUntilChanged();
    }

    public Observable<Boolean> userNameClearViewObservable() {
        return mUserNameVisible;
    }

    public Observable<Boolean> passwordClearViewObservable() {
        return mPasswordVisible;
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
    public Observable<Boolean> loginSuccessObservable() {
        return mLoginSuccess.filter(b -> b);
    }

    public void clearUserName() {
        mUserName.onNext("");
    }

    public void clearPassword() {
        mPassword.onNext("");
    }

    public void setUserName(String s) {
        mUserName.onNext(s);
    }

    public void setPassword(String s) {
        mPassword.onNext(s);
    }

}
