package com.iknow.module.datasource.network;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xiaojinzi.component.anno.ModuleAppAnno;
import com.xiaojinzi.component.application.IComponentApplication;

@ModuleAppAnno
public class DataSourceModuleApplication implements IComponentApplication {

    @Override
    public void onCreate(@NonNull Application app) {
    }

    @Override
    public void onDestroy() {
        ApiManager.getInstance().destory();
    }

}
