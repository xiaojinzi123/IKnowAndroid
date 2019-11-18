package com.iknow.module.main.view;

import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.iknow.module.base.ModuleConfig;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.base.view.Tip;
import com.iknow.module.main.R;
import com.iknow.module.main.databinding.MainHomeActBinding;
import com.iknow.module.main.vm.HomeViewModel;
import com.xiaojinzi.component.anno.RouterAnno;

import io.reactivex.Observable;

/**
 * 主界面
 */
@RouterAnno(
        path = ModuleConfig.Main.HOME
)
public class HomeAct extends BaseAct<HomeViewModel> {

    private MainHomeActBinding mBinding;

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.main_home_act);
        return mBinding.getRoot();
    }

    @Nullable
    @Override
    protected Class<? extends HomeViewModel> getViewModelClass() {
        return HomeViewModel.class;
    }

    @Override
    protected void onInit() {
        super.onInit();
        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setNavigationOnClickListener(v -> mBinding.drawerLayout.openDrawer(Gravity.LEFT));

        subscibeUi(mViewModel.getBannerSubject(), item -> {
            mView.tip(Tip.normal("成功了"));
        });

    }

    public void clickView(View view) {
        mViewModel.loadBanner();
    }

}
