package com.iknow.module.base.service.user;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.module.base.support.HotObservable;

import java.util.Optional;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface UserService {

    /**
     * 是否已经登录
     */
    boolean isLogin();

    /**
     * 获取用户信息, 可能为空, 既没有信号发射出来, 只有完成信号
     */
    @NonNull
    Maybe<UserInfoBean> getUserInfo();

    /**
     * 订阅登陆状态.
     *
     * @return 返回一个登陆状态的热信号, 当登陆状态发生变化的时候, 通知出去
     */
    @NonNull
    @HotObservable
    Observable<Boolean> subscribeLoginState();

    /**
     * 订阅用户信息, 比如有些地方需要显示用户信息的, 当用户模块更改了用户信息, 会通知出去
     *
     * @return 返回一个热信号, 发射用户的信息
     */
    @NonNull
    @HotObservable
    Observable<Optional<UserInfoBean>> subscribeUser();

    /**
     * 获取用户的默认头像 rsd
     */
    @DrawableRes
    int getDefaultUserAvatar();

}
