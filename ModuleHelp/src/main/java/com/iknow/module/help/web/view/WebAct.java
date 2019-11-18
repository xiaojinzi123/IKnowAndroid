package com.iknow.module.help.web.view;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.iknow.module.base.ModuleConfig;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.help.R;
import com.iknow.module.help.databinding.HelpWebActBinding;
import com.iknow.module.help.web.vm.WebViewModel;
import com.xiaojinzi.component.anno.FiledAutowiredAnno;
import com.xiaojinzi.component.anno.RouterAnno;

@RouterAnno(
        path = ModuleConfig.Help.WEB
)
public class WebAct extends BaseAct<WebViewModel> {

    private HelpWebActBinding mBinding;

    @FiledAutowiredAnno("data")
    String url;

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.help_web_act);
        return mBinding.getRoot();
    }

    @Override
    protected void onInjectView() {
        super.onInjectView();
    }

    @Nullable
    @Override
    protected Class<? extends WebViewModel> getViewModelClass() {
        return WebViewModel.class;
    }

    @Override
    protected void onInit() {
        super.onInit();

        if (!TextUtils.isEmpty(url)) {
            mBinding.web.loadUrl(url);
        }

    }

}
