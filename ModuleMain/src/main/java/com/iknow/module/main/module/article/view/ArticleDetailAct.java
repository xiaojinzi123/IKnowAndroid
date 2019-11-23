package com.iknow.module.main.module.article.view;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.main.R;
import com.iknow.module.main.databinding.MainArticleDetailActBinding;
import com.iknow.module.main.module.article.vm.ArticleDetailViewModel;
import com.xiaojinzi.component.anno.RouterAnno;

@RouterAnno(
        path = ModuleInfo.Main.ARTICLE_DETAIL
)
public class ArticleDetailAct extends BaseAct<ArticleDetailViewModel> {

    MainArticleDetailActBinding mBinding;

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.main_article_detail_act,
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
