package com.iknow.module.user.module.edit.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.base.support.HotObservableAnno;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.impl.service.RxServiceManager;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class UserInfoEditViewModel extends BaseViewModel {

    public UserInfoEditViewModel(@NonNull Application application) {
        super(application);

        disposables.add(
                RxServiceManager.with(UserService.class)
                        .flatMapMaybe(service -> service.getUserInfo())
                        .subscribe(userInfo -> {
                            mUserInfoSubject.onNext(userInfo);
                        })
        );

    }

    private BehaviorSubject<UserInfoBean> mUserInfoSubject =
            BehaviorSubject.create();


    @NonNull
    @HotObservableAnno("订阅用户信息, 这个用户信息不是全局的, 是此 VM 的")
    public Observable<UserInfoBean> subscribeUserInfo(){
        return mUserInfoSubject;
    }

}
