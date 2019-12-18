package com.iknow.module.user.module.info.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.base.support.HotObservableAnno;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.impl.service.ServiceManager;

import io.reactivex.Observable;

public class UserInfoViewModel extends BaseViewModel {

    public UserInfoViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @HotObservableAnno("订阅用户信息, 这个用户信息不是全局的, 是此 VM 的")
    public Observable<UserInfoBean> subscribeUserInfo() {
        return ServiceManager.get(UserService.class)
                .subscribeUser()
                .filter(item -> item.isPresent())
                .map(item -> item.get());
    }

}
