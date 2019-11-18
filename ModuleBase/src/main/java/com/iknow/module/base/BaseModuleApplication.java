package com.iknow.module.base;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xiaojinzi.component.anno.ModuleAppAnno;
import com.xiaojinzi.component.application.IComponentApplication;

@ModuleAppAnno
public class BaseModuleApplication implements IComponentApplication {

    @Override
    public void onCreate(@NonNull Application app) {
    }

    @Override
    public void onDestroy() {
    }

}
