package com.iknow.module.help.module.web.view;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.help.R;
import com.iknow.module.help.databinding.HelpWebActBinding;
import com.iknow.module.help.module.web.vm.WebViewModel;
import com.xiaojinzi.component.anno.FieldAutowiredAnno;
import com.xiaojinzi.component.anno.RouterAnno;

@RouterAnno(
        path = ModuleInfo.Help.WEB
)
public class WebAct extends BaseAct<WebViewModel> {

    private HelpWebActBinding mBinding;

    @FieldAutowiredAnno("data")
    String url;

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.help_web_act,
                null, false
        );
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

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mBinding.web.getSettings().setJavaScriptEnabled(true);
        mBinding.web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mBinding.web.setWebViewClient(new WebViewClient());
        mBinding.web.setWebChromeClient(new WebChromeClient());

        if (!TextUtils.isEmpty(url)) {
            mBinding.web.loadUrl(url);
        }

    }

}
