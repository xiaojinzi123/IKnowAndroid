package com.iknow.module.base.service.main;

import androidx.annotation.NonNull;

import com.iknow.module.base.support.HotObservableAnno;

import io.reactivex.Observable;

public interface HomeMenuService {

    /**
     * 订阅菜单的状态, 关闭或者打开
     */
    @NonNull
    @HotObservableAnno
    Observable<Boolean> subscribeMenuState();

    /**
     * 菜单是否关闭
     */
    boolean isMenuClosed();

    /**
     * 关闭菜单
     */
    void closeMenu();

    /**
     * 打开菜单
     */
    void openMenu();

}
