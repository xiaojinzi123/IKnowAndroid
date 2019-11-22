package com.iknow.module.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.lifecycle.Lifecycle;

import com.iknow.lib.beans.help.ActivityLifecycleBean;

import io.reactivex.subjects.PublishSubject;

public class CommonActivityLifecycleCallback
        implements Application.ActivityLifecycleCallbacks {

    public static
    PublishSubject<ActivityLifecycleBean> activityLifecycleBehaviorSubject
            = PublishSubject.create();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityLifecycleBehaviorSubject
                .onNext(
                        new ActivityLifecycleBean(
                                activity,
                                Lifecycle.Event.ON_CREATE
                        )
                );
    }

    @Override
    public void onActivityStarted(Activity activity) {
        activityLifecycleBehaviorSubject
                .onNext(
                        new ActivityLifecycleBean(
                                activity,
                                Lifecycle.Event.ON_START
                        )
                );
    }

    @Override
    public void onActivityResumed(Activity activity) {
        activityLifecycleBehaviorSubject
                .onNext(
                        new ActivityLifecycleBean(
                                activity,
                                Lifecycle.Event.ON_RESUME
                        )
                );
    }

    @Override
    public void onActivityPaused(Activity activity) {
        activityLifecycleBehaviorSubject
                .onNext(
                        new ActivityLifecycleBean(
                                activity,
                                Lifecycle.Event.ON_PAUSE
                        )
                );
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityLifecycleBehaviorSubject
                .onNext(
                        new ActivityLifecycleBean(
                                activity,
                                Lifecycle.Event.ON_STOP
                        )
                );
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        // empty
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityLifecycleBehaviorSubject
                .onNext(
                        new ActivityLifecycleBean(
                                activity,
                                Lifecycle.Event.ON_DESTROY
                        )
                );
    }

}
