package com.iknow.module.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.module.base.support.HotObservableAnno;

import java.util.Objects;
import java.util.Optional;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
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

    // token
    private BehaviorSubject<Optional<String>>
            tokenSubject = BehaviorSubject.createDefault(Optional.empty());

    // 用户信息
    private BehaviorSubject<Optional<UserInfoBean>>
            userInfoSubject = BehaviorSubject.createDefault(Optional.empty());

    /**
     * 订阅一个 Token
     */
    @NonNull
    @HotObservableAnno
    public Observable<Optional<String>> subscribeToken() {
        return tokenSubject;
    }

    /**
     * 订阅一个用户信息
     */
    @NonNull
    @HotObservableAnno
    public Observable<Optional<UserInfoBean>> subscribeUserInfo() {
        return userInfoSubject;
    }

    public Single<Boolean> isLogin() {
        return Single.just(
                userInfoSubject.getValue() != null
                        && userInfoSubject.getValue().isPresent()
        );
    }

    @NonNull
    public Completable updateUser(@Nullable UserInfoBean userInfoBean) {
        return Completable.fromAction(() -> {
            Objects.nonNull(userInfoBean);
            userInfoSubject.onNext(Optional.of(userInfoBean));
        });
    }

    public Completable updateUserAndToken(@NonNull String token, @NonNull UserInfoBean userInfoBean) {
        return Completable.fromAction(() -> {
            Objects.nonNull(token);
            Objects.nonNull(userInfoBean);
            tokenSubject.onNext(Optional.of(token));
            userInfoSubject.onNext(Optional.of(userInfoBean));
        });
    }

    @NonNull
    public Completable loginOut() {
        return Completable.fromAction(() -> {
            tokenSubject.onNext(Optional.empty());
            userInfoSubject.onNext(Optional.empty());
        });
    }

}
