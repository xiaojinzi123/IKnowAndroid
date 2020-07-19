package com.iknow.module.help

import android.content.Intent
import android.net.Uri
import com.iknow.module.base.ModuleInfo
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.impl.RouterRequest
import com.xiaojinzi.component.support.ParameterSupport
import java.lang.NullPointerException

object SystemTargetRouter {

    @RouterAnno(
        host = ModuleInfo.System.NAME,
        path = ModuleInfo.System.WEB
    )
    @JvmStatic
    fun webIntent(request: RouterRequest): Intent {
        var url: String = ParameterSupport
            .getString(request.bundle, "url") ?: throw NullPointerException("url is null")
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        intent.data = Uri.parse(url)
        return intent
    }

}