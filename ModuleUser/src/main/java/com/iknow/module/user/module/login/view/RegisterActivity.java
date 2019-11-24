package com.iknow.module.user.module.login.view;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.user.R;
import com.iknow.module.user.databinding.UserRegisterActivityBinding;
import com.iknow.module.user.module.login.vm.RegisterViewModel;
import com.xiaojinzi.component.anno.RouterAnno;

/**
 * 注册页面
 *
 * @author ljl
 */
@RouterAnno(
        path = ModuleInfo.User.LOGIN_REGISTER
)
public class RegisterActivity extends BaseAct<RegisterViewModel> {

    private UserRegisterActivityBinding mBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.user_register_activity;
    }

    @Override
    protected void onInjectView() {
        super.onInjectView();
        mBinding = UserRegisterActivityBinding.bind(findViewById(R.id.ll_root));
    }

    @Nullable
    @Override
    protected Class<? extends RegisterViewModel> getViewModelClass() {
        return RegisterViewModel.class;
    }

    @Override
    protected void onInit() {
        super.onInit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        initListener();
        subscibeUi(
                mViewModel.registerSuccessObservable(),
                b -> onRegisterSuccess()
        );
    }

    private void onRegisterSuccess() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void initListener() {
        mBinding.registerClickTv.setOnClickListener(v -> mViewModel.register());
    }

}
