package com.iknow.module.user.service;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.module.base.ActivityStack;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.service.datasource.DataSourceService;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.user.R;
import com.iknow.module.user.UserInfoManager;
import com.xiaojinzi.component.anno.ServiceAnno;
import com.xiaojinzi.component.impl.RxRouter;
import com.xiaojinzi.component.impl.service.RxServiceManager;

import java.util.Objects;
import java.util.Optional;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@ServiceAnno(UserService.class)
public class UserServiceImpl implements UserService {

    @Override
    public Single<Boolean> isLogin() {
        return UserInfoManager.getInstance().isLogin();
    }

    @NonNull
    @Override
    public Completable login() {
        Activity context = ActivityStack.getInstance().getBottomActivity();
        if (context == null) {
            return Completable.error(new NullPointerException("App没有正在运行的Activity"));
        }
        return isLogin()
                .flatMapCompletable(b -> {
                    if (b) {
                        return Completable.complete();
                    } else {
                        return RxRouter.with(context)
                                .host(ModuleInfo.User.NAME)
                                .path(ModuleInfo.User.LOGIN)
                                .requestCodeRandom()
                                .resultCodeMatchCall(Activity.RESULT_OK);
                    }
                });
    }

    @NonNull
    @Override
    public Completable loginOut() {
        return UserInfoManager.getInstance().loginOut();
    }

    @NonNull
    @Override
    public Maybe<String> getToken() {
        return UserInfoManager.getInstance().subscribeToken()
                .firstOrError()
                .flatMapMaybe(item -> {
                    if (item.isPresent()) {
                        return Maybe.just(item.get());
                    } else {
                        return Maybe.empty();
                    }
                });
    }

    @NonNull
    @Override
    public Maybe<UserInfoBean> getUserInfo() {
        return UserInfoManager.getInstance().subscribeUserInfo()
                .firstOrError()
                .flatMapMaybe(item -> {
                    if (item.isPresent()) {
                        return Maybe.just(item.get());
                    } else {
                        return Maybe.empty();
                    }
                });
    }

    @NonNull
    @Override
    public Maybe<Integer> getUserId() {
        return getUserInfo()
                .map(item -> item.getId());
    }

    @NonNull
    @Override
    public Observable<Boolean> subscribeLoginState() {
        return UserInfoManager
                .getInstance()
                .subscribeUserInfo()
                .map(item -> item.isPresent());
    }

    @NonNull
    @Override
    public Observable<Optional<UserInfoBean>> subscribeUser() {
        return UserInfoManager
                .getInstance()
                .subscribeUserInfo();
    }

    @Override
    public int getDefaultUserAvatar() {
        return R.drawable.user_default_avatar;
    }

    @Override
    public int getDefaultUserBg() {
        return R.drawable.user_default_background;
    }

    @NonNull
    @Override
    public Completable updateUserAndToken(@NonNull String token, @NonNull UserInfoBean userInfoBean) {
        Objects.nonNull(token);
        Objects.nonNull(userInfoBean);
        return UserInfoManager
                .getInstance()
                .updateUserAndToken(token, userInfoBean);
    }

    @NonNull
    @Override
    public Completable updateUser(@NonNull UserInfoBean userInfoBean) {
        Objects.nonNull(userInfoBean);
        return UserInfoManager
                .getInstance()
                .updateUser(userInfoBean);
    }

    @NonNull
    @Override
    public Completable signIn() {
        Completable signInObservable = Single
                .zip(
                        RxServiceManager.with(DataSourceService.class)
                                .doOnSuccess(item -> {
                                    System.out.println("213123");
                                }),
                        getUserId().toSingle()
                                .doOnSuccess(item -> {
                                    System.out.println("213123");
                                }),
                        (dataSourceService, userId) -> {
                            return dataSourceService.signIn(userId);
                        }
                )
                .flatMapCompletable(item -> item);
        return login()
                .observeOn(Schedulers.io())
                .andThen(signInObservable);
    }

}
