package com.iknow.module.user.module.edit.view;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.iknow.module.base.InterceptorInfo;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.user.R;
import com.iknow.module.user.databinding.UserUserInfoEditActBinding;
import com.iknow.module.user.module.edit.vm.UserInfoEditViewModel;
import com.xiaojinzi.component.anno.RouterAnno;
import com.xiaojinzi.component.impl.service.RxServiceManager;

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

    @Override
    protected void onInjectView() {
        super.onInjectView();
    }

    @Override
    protected void onInit() {
        super.onInit();
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        mBinding.btTest.setOnClickListener(view -> {

            RxServiceManager.with(UserService.class)
                    .flatMapMaybe(service -> service.getUserInfo())
                    .doOnSuccess(item -> item.setName("修改过的名称"))
                    .flatMapCompletable(item ->
                            RxServiceManager.with(UserService.class)
                                    .flatMapCompletable(innerItem -> innerItem.updateUser(item))
                    )
                    .subscribe();

        });

    }

}
