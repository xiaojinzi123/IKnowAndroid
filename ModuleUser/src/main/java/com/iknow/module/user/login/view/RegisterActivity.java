package com.iknow.module.user.login.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.base.view.Tip;
import com.iknow.module.base.view.inter.IBaseView;
import com.iknow.module.user.R;
import com.iknow.module.user.databinding.ActivityRegisterBinding;
import com.iknow.module.user.login.constants.LoginConstants;
import com.iknow.module.user.login.vm.RegisterViewModel;
import com.xiaojinzi.component.anno.RouterAnno;
import com.xiaojinzi.component.impl.Router;

/**
 * 注册页面
 *
 * @author ljl
 */
@RouterAnno(
        path = ModuleInfo.User.LOGIN_REGISTER
)
public class RegisterActivity extends BaseAct<RegisterViewModel> {

    private ActivityRegisterBinding mBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onInjectView() {
        super.onInjectView();
        mBinding = ActivityRegisterBinding.bind(findViewById(R.id.ll_root));
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
        //这里应该还需要搞一个密码的。
        Router.with(mContext)
                .host(ModuleInfo.User.NAME)
                .path(ModuleInfo.User.LOGIN)
                .putString(LoginConstants.KEY_USER_NAME, String.valueOf(mBinding.registerUserNameEt.getText()))
                .afterEventAction(this::finish)
                .forward();
    }

    private void initListener() {
        mBinding.registerClickTv.setOnClickListener(v -> mViewModel.register());
    }

    @NonNull
    @Override
    protected IBaseView onCreateBaseView() {
        final IBaseView target = super.onCreateBaseView();
        return new IBaseView.IBaseViewProxy(target) {
            @Override
            public void tip(@NonNull Tip tip) {
            }
        };
    }
}
