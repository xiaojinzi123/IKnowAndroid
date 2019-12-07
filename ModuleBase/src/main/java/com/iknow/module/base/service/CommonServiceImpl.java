package com.iknow.module.base.service;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.help.ActivityLifecycleBean;
import com.iknow.lib.beans.help.ClipboardContent;
import com.iknow.module.base.CommonActivityLifecycleCallback;
import com.xiaojinzi.component.anno.ServiceAnno;

import io.reactivex.Observable;

@ServiceAnno(CommonService.class)
public class CommonServiceImpl implements CommonService {

    @NonNull
    @Override
    public Observable<ActivityLifecycleBean> subscribeActivityLifecycle() {
        return CommonActivityLifecycleCallback.activityLifecycleBehaviorSubject;
    }

    @NonNull
    @Override
    public Observable<ClipboardContent> subscribeClipboard() {
        throw new UnsupportedOperationException();
    }

}
