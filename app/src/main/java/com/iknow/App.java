package com.iknow;

import android.content.Intent;

import androidx.multidex.MultiDexApplication;

import com.iknow.lib.tools.ToolsConfig;
import com.iknow.module.base.ModuleInfo;
import com.xiaojinzi.component.Component;
import com.xiaojinzi.component.impl.application.ModuleManager;
import com.xiaojinzi.component.support.RxErrorIgnoreUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

public class App extends MultiDexApplication {

    public void onCreate() {
        super.onCreate();

        // 初始化组件化
        Component.init(this, BuildConfig.DEBUG);
        // Component.openInitOptimize();
        ModuleManager.getInstance().registerArr(
                ModuleInfo.Base.NAME,
                ModuleInfo.App.NAME,
                ModuleInfo.Datasource.NAME,
                ModuleInfo.Welcome.NAME,
                ModuleInfo.Main.NAME,
                ModuleInfo.User.NAME,
                ModuleInfo.Help.NAME
        );
        RxErrorIgnoreUtil.ignoreError();

        // 初始化各个基础库
        ToolsConfig.init(this, BuildConfig.DEBUG);

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            Intent intent = new Intent("iknow_error");
            intent.putExtra("errorMsg", sw.toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        });

    }

}
