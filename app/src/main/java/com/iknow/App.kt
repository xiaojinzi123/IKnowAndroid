package com.iknow

import android.content.Intent
import android.os.Process
import androidx.multidex.MultiDexApplication
import com.iknow.lib.tools.ToolsConfig
import com.iknow.module.base.ModuleInfo
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.Config
import com.xiaojinzi.component.impl.application.ModuleManager
import com.xiaojinzi.component.support.RxErrorIgnoreUtil
import java.io.PrintWriter
import java.io.StringWriter

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        // 初始化组件化
        Component.init(
            BuildConfig.DEBUG,
            Config.with(this)
                .defaultScheme("iknow")
                .tipWhenUseApplication(true)
                .useRouteRepeatCheckInterceptor(true)
                .optimizeInit(true)
                .build()
        )
        // Component.openInitOptimize();
        ModuleManager.getInstance().registerArr(
            ModuleInfo.Base.NAME,
            ModuleInfo.App.NAME,
            ModuleInfo.Datasource.NAME,
            ModuleInfo.Welcome.NAME,
            ModuleInfo.Main.NAME,
            ModuleInfo.User.NAME,
            ModuleInfo.Help.NAME
        )
        RxErrorIgnoreUtil.ignoreError()
        // 初始化各个基础库
        ToolsConfig.init(this, BuildConfig.DEBUG)
        Thread.setDefaultUncaughtExceptionHandler { _: Thread?, e: Throwable ->
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            e.printStackTrace(pw)
            val intent = Intent("iknow_error")
            intent.putExtra("errorMsg", sw.toString())
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
            Process.killProcess(Process.myPid())
        }
    }
}