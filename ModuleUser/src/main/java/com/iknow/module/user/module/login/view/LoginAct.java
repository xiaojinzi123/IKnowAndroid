package com.iknow.module.user.module.login.view;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.base.view.Tip;
import com.iknow.module.base.view.inter.IBaseView;
import com.iknow.module.base.widget.TextWatcherAdapter;
import com.iknow.module.user.R;
import com.iknow.module.user.databinding.UserLoginActBinding;
import com.iknow.module.user.module.login.vm.LoginViewModel;
import com.xiaojinzi.component.anno.FiledAutowiredAnno;
import com.xiaojinzi.component.anno.RouterAnno;
import com.xiaojinzi.component.impl.Router;
import com.xiaojinzi.component.impl.RouterResult;
import com.xiaojinzi.component.support.CallbackAdapter;

import io.reactivex.functions.Consumer;

/**
 * 登录页面
 */
@RouterAnno(
        path = ModuleInfo.User.LOGIN
)
public class LoginAct extends BaseAct<LoginViewModel> {

    /**
     * 0 表示跳转过来是登陆的, 并且返回 {@link Activity#RESULT_OK}
     * 1 表示启动登陆, 会跳转到主页
     */
    @FiledAutowiredAnno("businessType")
    int businessType = 0;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                b -> onLoginSuccess()
        );

    }

    private void initListener() {
        mBinding.etUsername.addTextChangedListener(usernameTextWatcher);
        mBinding.etPassword.addTextChangedListener(passwordWatcher);
        mBinding.ivUsernameClear.setOnClickListener(v -> mViewModel.clearUserName());
        mBinding.ivPasswordClear.setOnClickListener(v -> mViewModel.clearPassword());
        mBinding.btnLogin.setOnClickListener(v -> mViewModel.login());
        mBinding.loginRegisterTv.setOnClickListener(v -> goToRegisterAccount());
    }

    private void goToRegisterAccount() {
        Router.with(mContext)
                .host(ModuleInfo.User.NAME)
                .path(ModuleInfo.User.LOGIN_REGISTER)
                .requestCodeRandom()
                .forwardForResultCodeMatch(new CallbackAdapter() {
                    @Override
                    public void onSuccess(@NonNull RouterResult result) {
                        super.onSuccess(result);
                        onLoginSuccess();
                    }
                }, Activity.RESULT_OK);
    }

    private void initTabLayout() {
    }

    private void onLoginSuccess() {
        /*if (true) {
            RxServiceManager.with(SmsService.class)
                    // .flatMapCompletable(service -> service.sendSms("17512035695"))
                    // .flatMapCompletable(service -> service.sendSms("15868909041"))
                    .flatMapCompletable(service -> service.sendSms("15857913627"))
                    //.flatMapCompletable(service -> service.sendSms("16621065801"))
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            System.out.println("212313");
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            System.out.println("123123");
                        }
                    });
            return;
        }*/
        if (businessType == 1) {
            Router.with(this)
                    .host(ModuleInfo.Main.NAME)
                    .path(ModuleInfo.Main.HOME)
                    .afterJumpAction(this::finish)
                    .forward();
        } else {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @NonNull
    @Override
    protected IBaseView onCreateBaseView() {
        final IBaseView target = super.onCreateBaseView();
        return new IBaseView.IBaseViewProxy(target) {
            @Override
            public void tip(@NonNull Tip tip) {
                if (tip.getTipEnum() == Tip.TipEnum.Error) {
                    mBinding.tvError.setText(tip.getTip());
                } else {
                    super.tip(tip);
                }
            }
        };
    }

}
