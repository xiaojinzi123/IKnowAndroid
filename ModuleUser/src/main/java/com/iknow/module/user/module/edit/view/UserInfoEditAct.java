package com.iknow.module.user.module.edit.view;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.iknow.module.base.InterceptorInfo;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.user.R;
import com.iknow.module.user.databinding.UserUserInfoEditActBinding;
import com.iknow.module.user.module.edit.vm.UserInfoEditViewModel;
import com.xiaojinzi.component.anno.RouterAnno;

/**
 * 此界面改的可不只是远程的用户信息.
 * 同时也是对全局的用户信息更改并且重新发射信号的一个重要界面
 */
@RouterAnno(
        path = ModuleInfo.User.EDIT,
        // 需要登陆才能进来的
        interceptorNames = InterceptorInfo.USER_LOGIN
)
public class UserInfoEditAct extends BaseAct<UserInfoEditViewModel> {

    UserUserInfoEditActBinding mBinding;

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.user_user_info_edit_act,
                null, false
        );
        return mBinding.getRoot();
    }

    @Nullable
    @Override
    protected Class<? extends UserInfoEditViewModel> getViewModelClass() {
        return UserInfoEditViewModel.class;
    }

    @Override
    protected void onInjectView() {
        super.onInjectView();
    }

    @Override
    protected void onInit() {
        super.onInit();
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        subscibeUi(mViewModel.subscribeUserInfo(), userInfo -> {
            mBinding.tvUserName.setText(userInfo.getName());
            Glide.with(mContext)
                    .load(userInfo.getBackgroundUrl())
                    .into(mBinding.ivBg);
            Glide.with(mContext)
                    .load(userInfo.getAvatar())
                    .circleCrop()
                    .into(mBinding.ivAvatar);
        });

    }

}
