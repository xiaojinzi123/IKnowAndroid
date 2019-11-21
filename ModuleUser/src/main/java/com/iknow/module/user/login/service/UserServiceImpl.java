package com.iknow.module.user.login.service;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.user.R;
import com.iknow.module.user.UserInfoManager;
import com.xiaojinzi.component.anno.ServiceAnno;

import java.util.Optional;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

@ServiceAnno(UserService.class)
public class UserServiceImpl implements UserService {

    @Override
    public boolean isLogin() {
        return UserInfoManager.getInstance().isLogin();
    }

    @NonNull
    @Override
    public Maybe<UserInfoBean> getUserInfo() {
        return UserInfoManager.getInstance().subscribeUserInfo()
                .firstOrError()
                .flatMapMaybe(userInfoBeanOptional -> {
                    if (userInfoBeanOptional.isPresent()) {
                        return Maybe.just(userInfoBeanOptional.get());
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

}
