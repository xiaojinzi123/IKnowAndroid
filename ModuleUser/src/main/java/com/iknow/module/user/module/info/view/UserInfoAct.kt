package com.iknow.module.user.module.info.view

import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.iknow.lib.beans.user.UserInfoBean
import com.iknow.module.base.InterceptorInfo
import com.iknow.module.base.ModuleInfo
import com.iknow.module.base.view.BaseAct
import com.iknow.module.ui.widget.UIBottomMenu
import com.iknow.module.user.R
import com.iknow.module.user.databinding.UserInfoActBinding
import com.iknow.module.user.module.info.vm.UserInfoViewModel
import com.xiaojinzi.component.anno.RouterAnno

@RouterAnno(
    path = ModuleInfo.User.INFO,
    interceptorNames = [InterceptorInfo.USER_LOGIN]
)
class UserInfoAct : BaseAct<UserInfoViewModel?>() {
    var mBinding: UserInfoActBinding? = null
    override fun getLayoutView(): View {
        mBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.user_info_act,
            null, false
        )
        return mBinding!!.getRoot()
    }

    override fun getViewModelClass(): Class<out UserInfoViewModel>? {
        return UserInfoViewModel::class.java
    }

    override fun onInit() {
        super.onInit()
        setSupportActionBar(mBinding!!.toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        subscibeUi(
            mViewModel!!.subscribeUserInfo()
        ) { userInfo: UserInfoBean ->
            mBinding!!.tvUserName.text = userInfo.name
            Glide.with(mContext)
                .load(userInfo.backgroundUrl)
                .into(mBinding!!.ivBg)
            Glide.with(mContext)
                .load(userInfo.avatar)
                .circleCrop()
                .into(mBinding!!.ivAvatar)
        }
        // 监听
        mBinding!!.ivBg.setOnClickListener {
            UIBottomMenu(mContext)
                .show()
        }
    }
}