package com.iknow.module.main.module.setting.view;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.main.R;
import com.iknow.module.main.databinding.MainSettingActBinding;
import com.xiaojinzi.component.anno.RouterAnno;

@RouterAnno(
        path = ModuleInfo.Main.SETTING
)
public class SettingAct extends BaseAct {

    MainSettingActBinding mBinding;

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.main_setting_act,
                null, false
        );
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.main_setting_act);
        return mBinding.getRoot();
    }

    @Override
    protected void onInit() {
        super.onInit();
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onInjectView() {
        super.onInjectView();
    }

}
