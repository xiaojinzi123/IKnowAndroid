package com.iknow.module.main.view;

import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.iknow.module.base.ModuleConfig;
import com.iknow.module.base.view.BaseAct;
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
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    public void clickView(View view) {
        mViewModel.testLoading();
    }

}
