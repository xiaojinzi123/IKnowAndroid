package com.iknow.module.base;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xiaojinzi.component.anno.ModuleAppAnno;
import com.xiaojinzi.component.application.IComponentApplication;

@ModuleAppAnno
public class BaseModuleApplication implements IComponentApplication {

    private CommonActivityLifecycleCallback commonActivityLifecycleCallback;

    @Override
    public void onCreate(@NonNull Application app) {
        commonActivityLifecycleCallback = new CommonActivityLifecycleCallback();
        app.registerActivityLifecycleCallbacks(commonActivityLifecycleCallback);
    }

    @Override
    public void onDestroy() {
        commonActivityLifecycleCallback = null;
    }

}
