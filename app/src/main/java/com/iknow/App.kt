package com.iknow

import android.app.Activity
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.iknow.lib.tools.ToolsConfig
import com.iknow.module.base.ModuleInfo
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.impl.application.ModuleManager
import com.xiaojinzi.component.support.LogUtil
import com.xiaojinzi.component.support.RxErrorIgnoreUtil

/**
 * @TODO: 首页登陆的时候, 拦截登陆对需要销毁的界面不合适. 需要从登陆界面启动
 * @TODO: ViewModel 由于官方的库, 和 Activity 有关联的 Fragment, Dialog 等获取到的都是同一个对象. 但是BaseAct 和 BaseFragment 都会对loading 和 tip 响应. 需要解决
 *
 *
 * @author : xiaojinzi 30212
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // 初始化组件化
        Component.init(this, BuildConfig.DEBUG)
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

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
                if (activity != null) {
                    LogUtil.log("ljl", "activity:" + activity.localClassName)
                }
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            }

        })
    }

}
