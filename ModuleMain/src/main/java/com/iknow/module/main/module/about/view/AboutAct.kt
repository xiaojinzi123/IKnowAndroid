package com.iknow.module.main.module.about.view

import android.view.View
import androidx.databinding.DataBindingUtil
import com.iknow.lib.beans.help.ClipboardContent
import com.iknow.lib.tools.toastShort
import com.iknow.module.base.ModuleInfo
import com.iknow.module.base.service.CommonService
import com.iknow.module.base.view.BaseAct
import com.iknow.module.base.vm.BaseViewModel
import com.iknow.module.main.R
import com.iknow.module.main.databinding.MainAboatActBinding
import com.iknow.module.main.module.about.vm.AboutViewModel
import com.iknow.module.main.module.article.vm.ArticleDetailViewModel
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.impl.Router
import com.xiaojinzi.component.impl.service.RxServiceManager
import io.reactivex.functions.Action

@RouterAnno(path = ModuleInfo.Main.ABOAT)
class AboutAct : BaseAct<AboutViewModel>() {

    private var mBinding: MainAboatActBinding? = null

    override fun getLayoutView(): View {
        mBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.main_aboat_act,
            null, false
        )
        return mBinding!!.root
    }

    override fun onInit() {
        super.onInit()
        setSupportActionBar(mBinding!!.toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)

        mBinding!!.rlServiceAddress.setOnClickListener {
            Router.with(this)
                .url("http://xiaojinzi.tpddns.cn:18080/iknow/swagger-ui.html")
                .putBoolean("isUseSystem", true)
                .forward()
        }

        mBinding!!.rlServiceAddress.setOnLongClickListener {
            RxServiceManager.with(CommonService::class.java)
                .flatMapCompletable { it.setClipboard(ClipboardContent()) }
                .subscribe(fun() {
                    toastShort(getString(R.string.resource_copy_success))
                })
            return@setOnLongClickListener true
        }

    }

}