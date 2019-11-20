package com.iknow.module.user.login.service;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iknow.module.base.service.user.UserInfoMenuService;
import com.xiaojinzi.component.anno.ServiceAnno;

@ServiceAnno(UserInfoMenuService.class)
public class UserInfoMenuServiceImpl implements UserInfoMenuService {

    @Nullable
    private View contentView;

    @NonNull
    @Override
    public View getView(@NonNull Context act) {
        if (contentView == null) {
            //
        }
        return contentView;
    }

    @Override
    public void destory() {
        contentView = null;
    }

}
