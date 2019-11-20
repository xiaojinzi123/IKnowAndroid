package com.iknow.module.user;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.module.base.support.HotObservable;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class UserInfoManager {

    private static volatile UserInfoManager mInstance;

    private UserInfoManager() {
    }


    public static UserInfoManager getInstance() {
        if (mInstance == null) {
            synchronized (UserInfoManager.class) {
                if (mInstance == null) {
                    mInstance = new UserInfoManager();
                }
            }
        }
        return mInstance;
    }

    // 用户信息
    private BehaviorSubject<Optional<UserInfoBean>> userInfoSubject = BehaviorSubject.create();

    /**
     * 订阅一个用户信息
     */
    @NonNull
    @HotObservable
    public Observable<Optional<UserInfoBean>> subscribeUserInfo() {
        return userInfoSubject;
    }

    public boolean isLogin() {
        return userInfoSubject.getValue().isPresent();
    }
}
