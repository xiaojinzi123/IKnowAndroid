package com.iknow.module.main.module.aboat.view;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.main.R;
import com.iknow.module.main.databinding.MainAboatActBinding;
import com.iknow.module.main.module.article.vm.ArticleDetailViewModel;
import com.xiaojinzi.component.anno.RouterAnno;

@RouterAnno(
        path = ModuleInfo.Main.ABOAT
)
public class AboatAct extends BaseAct {

    MainAboatActBinding mBinding;

    @Nullable
    @Override
    protected Class<? extends ArticleDetailViewModel> getViewModelClass() {
        return ArticleDetailViewModel.class;
    }

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.main_aboat_act,
                null, false
        );
        return mBinding.getRoot();
    }

    @Override
    protected void onInit() {
        super.onInit();
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

}
