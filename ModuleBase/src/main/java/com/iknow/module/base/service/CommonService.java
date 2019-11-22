package com.iknow.module.base.service;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.help.ActivityLifecycleBean;
import com.iknow.module.base.support.HotObservableAnno;

import io.reactivex.Observable;

public interface CommonService {

    /**
     * 订阅 {@link Activity} 的生命周期, 每一个界面的生命周期变化这里都能监听到.
     * 这是一个热信号
     */
    @NonNull
    @HotObservableAnno
    Observable<ActivityLifecycleBean> subscribeActivityLifecy();

}
