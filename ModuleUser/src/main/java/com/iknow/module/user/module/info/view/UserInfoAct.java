package com.iknow.module.user.module.info.view;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.iknow.module.base.InterceptorInfo;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.user.R;
import com.iknow.module.user.databinding.UserInfoActBinding;
import com.iknow.module.user.module.edit.vm.UserInfoEditViewModel;
import com.xiaojinzi.component.anno.RouterAnno;

@RouterAnno(
        path = ModuleInfo.User.INFO,
        // 需要登陆才能进来的
        interceptorNames = InterceptorInfo.USER_LOGIN
)
public class UserInfoAct extends BaseAct {

    UserInfoActBinding mBinding;

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.user_info_act,
                null, false
        );
        return mBinding.getRoot();
    }

    @Nullable
    @Override
    protected Class<? extends UserInfoEditViewModel> getViewModelClass() {
        return UserInfoEditViewModel.class;
    }

}
