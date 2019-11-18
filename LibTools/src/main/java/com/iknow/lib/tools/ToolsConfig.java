package com.iknow.lib.tools;

import android.app.Application;
import androidx.annotation.NonNull;

/**
 * 配置类,这样子就不用经常传 Application 的 Context了
 * time   : 2018/12/11
 *
 * @author : xiaojinzi 30212
 */
public class ToolsConfig {

    @NonNull
    private static Application app;

    private static boolean isDebug;

    public static void init(@NonNull Application app, boolean isDebug) {
        if (app == null) {
            new NullPointerException("the application is null");
        }
        ToolsConfig.app = app;
        ToolsConfig.isDebug = isDebug;
    }

    @NonNull
    public static Application getApp() {
        return app;
    }

}
