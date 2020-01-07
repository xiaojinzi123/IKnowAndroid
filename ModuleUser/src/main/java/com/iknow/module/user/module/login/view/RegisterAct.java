package com.iknow.module.user.module.login.view;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.iknow.lib.tools.ResourceUtil;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.base.view.BusinessError;
import com.iknow.module.base.widget.TextWatcherAdapter;
import com.iknow.module.user.R;
import com.iknow.module.user.databinding.UserRegisterActBinding;
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
public class RegisterAct extends BaseAct<RegisterViewModel> {

    UserRegisterActBinding mBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.user_register_act;
    }

    @Override
    protected void onInjectView() {
        super.onInjectView();
        mBinding = UserRegisterActBinding.bind(findViewById(R.id.ll_root));
    }

    @Nullable
    @Override
    protected Class<? extends RegisterViewModel> getViewModelClass() {
        return RegisterViewModel.class;
    }

    @Override
    protected void onInit() {
        super.onInit();
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        subscibeUi(
                mViewModel.subscribeUserNameErrorMsgObservable(),
                errorMsg -> mBinding.tilUserName.setError(errorMsg.isPresent() ? errorMsg.get() : null)
        );
        subscibeUi(
                mViewModel.subscribePasswordErrorMsgObservable(),
                errorMsg -> mBinding.tilPasswordRepeat.setError(errorMsg.isPresent() ? errorMsg.get() : null)
        );
        subscibeUi(
                mViewModel.subscribeCheckCodeImageObservable(),
                url -> {
                    Glide.with(mContext)
                            .load(url)
                            .placeholder(R.drawable.user_common_check_code_fail)
                            .into(mBinding.ivCheckCode);
                }
        );
        subscibeUi(
                mViewModel.subscribeSuccessObservable(),
                b -> onRegisterSuccess()
        );
        subscibeUi(
                mViewModel.subscribeCanCommitObservable(),
                b -> mBinding.registerClickTv.setEnabled(b)
        );
        initListener();
    }

    @Override
    protected void onBusinessErrorSolve(@NonNull BusinessError businessError) {
        super.onBusinessErrorSolve(businessError);
        if (RegisterViewModel.BUSINESS_ERROR_CHECK_CODE == businessError.getType()) {
            mBinding.tlCheckCode.setError(businessError.getMsg());
        }
    }

    private void onRegisterSuccess() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void initListener() {
        mBinding.tietUserName.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                mViewModel.setUserName(s.toString());
            }
        });
        mBinding.tietPassword.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                mViewModel.setPassword(s.toString());
            }
        });
        mBinding.tietPasswordRepeat.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                mViewModel.setPasswordRepeat(s.toString());
            }
        });
        mBinding.tietCheckCode.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                mViewModel.setCheckCode(s.toString());
            }
        });
        mBinding.registerClickTv.setOnClickListener(v -> mViewModel.register());
        mBinding.ivCheckCode.setOnClickListener(v -> mViewModel.refreshCheckCode());
    }

}
