package com.iknow

import androidx.multidex.MultiDexApplication
import com.iknow.lib.tools.ToolsConfig
import com.iknow.module.base.ModuleConfig
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.impl.application.ModuleManager
import com.xiaojinzi.component.support.RxErrorIgnoreUtil

/**
 * time   : 2019/03/27
 *
 * @author : xiaojinzi 30212
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // 初始化组件化
        Component.init(this, BuildConfig.DEBUG)
        ModuleManager.getInstance().registerArr(
            ModuleConfig.App.NAME,
            ModuleConfig.Datasource.NAME,
            ModuleConfig.Welcome.NAME,
            ModuleConfig.Main.NAME,
            ModuleConfig.User.NAME,
            ModuleConfig.Help.NAME
        )
        RxErrorIgnoreUtil.ignoreError()

        // 初始化各个基础库
        ToolsConfig.init(this, BuildConfig.DEBUG)

    }

}
