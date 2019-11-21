package com.iknow.module.user.login.view;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.service.help.HelpService;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.base.view.Tip;
import com.iknow.module.base.view.inter.IBaseView;
import com.iknow.module.base.widget.TextWatcherAdapter;
import com.iknow.module.user.R;
import com.iknow.module.user.databinding.UserLoginActBinding;
import com.iknow.module.user.login.vm.LoginViewModel;
import com.xiaojinzi.component.anno.RouterAnno;
import com.xiaojinzi.component.impl.service.RxServiceManager;

import io.reactivex.functions.Consumer;

/**
 * 登录页面
 */
@RouterAnno(
        path = ModuleInfo.User.LOGIN
)
public class LoginAct extends BaseAct<LoginViewModel> {

    private UserLoginActBinding mBinding;    // DataBinding 对象

    private TextWatcher usernameTextWatcher = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            mViewModel.setUserName(s.toString());
        }
    };

    private TextWatcher passwordWatcher = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            mViewModel.setPassword(s.toString());
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.user_login_act;
    }

    @Override
    protected void onInjectView() {
        super.onInjectView();
        mBinding = UserLoginActBinding.bind(findViewById(R.id.view_root));
    }

    @Nullable
    @Override
    protected Class<? extends LoginViewModel> getViewModelClass() {
        return LoginViewModel.class;
    }

    @Override
    protected void onInit() {
        super.onInit();
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initListener();
        initTabLayout();

        subscibeUi(
                mViewModel.userName(),
                s -> {
                    mBinding.etUsername.removeTextChangedListener(usernameTextWatcher);
                    mBinding.etUsername.setText(s);
                    mBinding.etUsername.setSelection(mBinding.etUsername.getText().length());
                    mBinding.etUsername.addTextChangedListener(usernameTextWatcher);
                }
        );

        subscibeUi(
                mViewModel.password(),
                s -> {
                    mBinding.etPassword.removeTextChangedListener(passwordWatcher);
                    mBinding.etPassword.setText(s);
                    mBinding.etPassword.setSelection(mBinding.etPassword.getText().length());
                    mBinding.etPassword.addTextChangedListener(passwordWatcher);
                }
        );

        subscibeUi(
                mViewModel.userNameClearViewObservable(),
                b -> mBinding.ivUsernameClear.setVisibility(b ? View.VISIBLE : View.INVISIBLE)
        );

        subscibeUi(
                mViewModel.passwordClearViewObservable(),
                b -> mBinding.ivPasswordClear.setVisibility(b ? View.VISIBLE : View.INVISIBLE)
        );

        subscibeUi(mViewModel.canCommitObservable(), new Consumer<Boolean>() {
            @Override
            public void accept(Boolean b) throws Exception {
                mBinding.btnLogin.setEnabled(b);
            }
        });

        subscibeUi(
                mViewModel.loginSuccessObservable(),
                b -> returnData()
        );

    }

    private void initListener() {
        mBinding.etUsername.addTextChangedListener(usernameTextWatcher);
        mBinding.etPassword.addTextChangedListener(passwordWatcher);
        mBinding.ivUsernameClear.setOnClickListener(v -> mViewModel.clearUserName());
        mBinding.ivPasswordClear.setOnClickListener(v -> mViewModel.clearPassword());
        mBinding.btnLogin.setOnClickListener(v -> mViewModel.login());
    }

    private void initTabLayout() {
    }

    private void returnData() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();

        /*RxServiceManager.with(HelpService.class)
                .flatMap(item -> item.phoneCheck(this))
                .subscribe(item -> {
                    System.out.println("2313123");
                });*/

    }

    @NonNull
    @Override
    protected IBaseView onCreateBaseView() {
        final IBaseView target = super.onCreateBaseView();
        return new IBaseView.IBaseViewProxy(target){
            @Override
            public void tip(@NonNull Tip tip) {
                if (tip.getTipEnum() == Tip.TipEnum.Error) {
                    mBinding.tvError.setText(tip.getTip());
                }else {
                    super.tip(tip);
                }
            }
        };
    }

}
