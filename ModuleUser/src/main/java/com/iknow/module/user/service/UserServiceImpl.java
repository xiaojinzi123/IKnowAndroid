package com.iknow.module.user.service;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.user.R;
import com.iknow.module.user.UserInfoManager;
import com.xiaojinzi.component.anno.ServiceAnno;

import java.util.Objects;
import java.util.Optional;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

@ServiceAnno(UserService.class)
public class UserServiceImpl implements UserService {

    @Override
    public boolean isLogin() {
        return UserInfoManager.getInstance().isLogin();
    }

    @NonNull
    @Override
    public Maybe<String> getToken() {
        return UserInfoManager.getInstance().subscribeToken()
                .firstOrError()
                .flatMapMaybe(item -> {
                    if (item.isPresent()) {
                        return Maybe.just(item.get());
                    }else {
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
                    }else {
                        return Maybe.empty();
                    }
                });
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

}
