package com.iknow.module.base.service.user;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.iknow.module.base.support.ViewDestory;

public interface UserInfoMenuService extends ViewDestory {

    /**
     * 获取显示的视图
     *
     * @param act 对应的 Context, 不能是 {@link android.app.Application}
     */
    @NonNull
    View getView(@NonNull Context act);

}
