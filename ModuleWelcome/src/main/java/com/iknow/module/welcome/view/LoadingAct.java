package com.iknow.module.welcome.view;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.welcome.R;
import com.iknow.module.welcome.vm.LoadingViewModel;
import com.xiaojinzi.component.impl.Router;

/**
 * Loading 页面
 */
public class LoadingAct extends BaseAct<LoadingViewModel> {

    TextView tv_counter;

    @Override
    protected int getLayoutId() {
        return R.layout.welcome_loading_act;
    }

    @Override
    protected void onInjectView() {
        super.onInjectView();
        tv_counter = findViewById(R.id.tv_counter);
    }

    @Nullable
    @Override
    protected Class<? extends LoadingViewModel> getViewModelClass() {
        return LoadingViewModel.class;
    }

    @Override
    protected void onInit() {
        super.onInit();

        subscibeUi(
                mViewModel.restCountObservable(),
                value -> tv_counter.setText(String.valueOf(value))
        );

        subscibeUi(
                mViewModel.countdownObservable(),
                () -> Router.with(mContext)
                        .host(ModuleInfo.Main.NAME)
                        .path(ModuleInfo.Main.HOME)
                        .afterJumpAction(() -> finish())
                        .forward()
        );

    }

}
