package com.iknow.module.user.module.login.vm;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.user.CheckCodeBean;
import com.iknow.lib.beans.user.LoginBean;
import com.iknow.lib.beans.user.RegisterBeanReq;
import com.iknow.lib.tools.ResourceUtil;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.base.support.CompleableObserverAdapter;
import com.iknow.module.base.support.ErrorUtil;
import com.iknow.module.base.support.HotObservableAnno;
import com.iknow.module.base.support.SingleObserverAdapter;
import com.iknow.module.base.view.BusinessError;
import com.iknow.module.base.view.Tip;
import com.iknow.module.base.vm.BaseViewModel;
import com.iknow.module.user.R;
import com.xiaojinzi.component.impl.service.RxServiceManager;

import java.util.Optional;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * @Author：briskemen time：2019/11/23
 */
public class RegisterViewModel extends BaseViewModel {

    public static final int BUSINESS_ERROR_CHECK_CODE = 1;

    private BehaviorSubject<Optional<String>> mUserNameErrorMsgSubject = BehaviorSubject.create();
    private BehaviorSubject<Optional<String>> mPasswordErrorMsgSubject = BehaviorSubject.create();

    private BehaviorSubject<String> mUserNameVOSubject = BehaviorSubject.createDefault("");
    private BehaviorSubject<String> mPasswordVOSubject = BehaviorSubject.createDefault("");
    private BehaviorSubject<String> mPasswordRepeatVOSubject = BehaviorSubject.createDefault("");
    private BehaviorSubject<String> mCheckCodeVOSubject = BehaviorSubject.createDefault("");
    private BehaviorSubject<CheckCodeBean> checkCodeDTOSubject = BehaviorSubject.create();

    /**
     * 注册按钮是否有用
     */
    private Subject<Boolean> mCommitEnableSubject = BehaviorSubject.createDefault(false);

    /**
     * 成功的信号,一直都会发送 true 这个信号
     */
    private Subject<Boolean> mRegisterSuccessSubject = BehaviorSubject.createDefault(false);

    public RegisterViewModel(@NonNull Application application) {
        super(application);

        disposables.add(
                mUserNameVOSubject
                        .flatMapSingle(userName ->
                                RxServiceManager.with(DataSourceService.class)
                                        .flatMap(service -> service.isUserNameExist(userName))
                                        .map(b -> {
                                            if (b) {
                                                return Optional.of("用户名已经存在");
                                            } else {
                                                return Optional.<String>empty();
                                            }
                                        })
                                        .onErrorReturn(error -> {
                                            String errorMsg = ErrorUtil.getServiceExceptionMsg(error);
                                            if (errorMsg == null) {
                                                Optional.<String>empty();
                                            }
                                            return Optional.of(errorMsg);
                                        })
                                        .subscribeOn(Schedulers.io())
                        )
                        .subscribe(errorMsgOptional -> {
                            mUserNameErrorMsgSubject.onNext(errorMsgOptional);
                        })
        );

        disposables.add(
                Observable
                        .combineLatest(
                                mPasswordVOSubject, mPasswordRepeatVOSubject,
                                (s1, s2) -> s1.equals(s2) ? Optional.<String>empty() : Optional.of("两次密码输入不相同")
                        )
                        .subscribe(result -> {
                            mPasswordErrorMsgSubject.onNext(result);
                        })
        );

        disposables.add(
                Observable
                        .combineLatest(
                                mUserNameErrorMsgSubject, mPasswordErrorMsgSubject
                                , mCheckCodeVOSubject,
                                (userNameErrorMsg, passwordErrorMsg, checkCode) -> {
                                    return !userNameErrorMsg.isPresent() &&
                                            !passwordErrorMsg.isPresent() &&
                                            !TextUtils.isEmpty(checkCode);
                                }
                        )
                        .subscribe(b -> {
                            mCommitEnableSubject.onNext(b);
                        })
        );

        refreshCheckCode();
    }

    /**
     * 注册的流程
     */
    public void register() {

        RegisterBeanReq registerBeanReq = new RegisterBeanReq();
        registerBeanReq.setUserName(mUserNameVOSubject.getValue());
        registerBeanReq.setPassword(mPasswordVOSubject.getValue());
        registerBeanReq.setCheckCodeUid(checkCodeDTOSubject.getValue().getUid());
        registerBeanReq.setCheckCode(mCheckCodeVOSubject.getValue());

        Single<LoginBean> registerObservable = RxServiceManager.with(DataSourceService.class)
                .flatMap(service -> service.register(registerBeanReq));

        Single<UserService> userServiceObservable = RxServiceManager.with(UserService.class);

        Completable observable = Single
                .zip(
                        registerObservable, userServiceObservable,
                        (loginBean, userService) -> userService.updateUserAndToken(loginBean.getToken(), loginBean.getUserInfo())
                )
                .flatMapCompletable(item -> item);

        subscribe(observable, new CompleableObserverAdapter(
                        () -> mRegisterSuccessSubject.onNext(true),
                        error -> {
                            String msg = ErrorUtil.getServiceExceptionMsg(error);
                            if (msg == null) {
                                normalErrorSolve(error);
                            } else {
                                tipSubject.onNext(Tip.msgbox(msg));
                            }
                        }
                )
        );

    }

    public void refreshCheckCode() {

        Single<CheckCodeBean> observable = RxServiceManager.with(DataSourceService.class)
                .flatMap(service -> service.getCheckCode());

        subscribe(observable, new SingleObserverAdapter<>(result -> {
            checkCodeDTOSubject.onNext(result);
        }, error -> {
            businessErrorSubject.onNext(BusinessError.build(
                    BUSINESS_ERROR_CHECK_CODE,
                    ResourceUtil.getString(R.string.resource_fail_to_get_checkcode)
            ));
        }));

    }

    @NonNull
    @HotObservableAnno("订阅用户名的错误信息")
    public Observable<Optional<String>> subscribeUserNameErrorMsgObservable() {
        return mUserNameErrorMsgSubject;
    }

    @NonNull
    @HotObservableAnno("订阅密码的错误信息")
    public Observable<Optional<String>> subscribePasswordErrorMsgObservable() {
        return mPasswordErrorMsgSubject;
    }

    @NonNull
    @HotObservableAnno("订阅验证码图片")
    public Observable<String> subscribeCheckCodeImageObservable() {
        return checkCodeDTOSubject
                .map(item -> item.getUrl());
    }

    public Observable<Boolean> subscribeCanCommitObservable() {
        return mCommitEnableSubject;
    }

    /**
     * 订阅是否成功的 Observable
     * 只会返回 true 的信号
     *
     * @return
     */
    @NonNull
    @HotObservableAnno
    public Observable<Boolean> subscribeSuccessObservable() {
        return mRegisterSuccessSubject.filter(b -> b);
    }

    public void setUserName(String s) {
        mUserNameVOSubject.onNext(s);
    }

    public void setCheckCode(String value) {
        mCheckCodeVOSubject.onNext(value);
    }

    public void setPassword(String password) {
        mPasswordVOSubject.onNext(password);
    }

    public void setPasswordRepeat(String password) {
        mPasswordRepeatVOSubject.onNext(password);
    }

}
