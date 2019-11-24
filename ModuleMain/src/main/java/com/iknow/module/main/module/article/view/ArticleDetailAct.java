package com.iknow.module.main.module.article.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.Target;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.main.R;
import com.iknow.module.main.databinding.MainArticleDetailActBinding;
import com.iknow.module.main.module.article.vm.ArticleDetailViewModel;
import com.xiaojinzi.component.anno.FiledAutowiredAnno;
import com.xiaojinzi.component.anno.RouterAnno;

import io.noties.markwon.AbstractMarkwonPlugin;
import io.noties.markwon.Markwon;
import io.noties.markwon.core.MarkwonTheme;
import io.noties.markwon.image.AsyncDrawable;
import io.noties.markwon.image.glide.GlideImagesPlugin;

@RouterAnno(
        path = ModuleInfo.Main.ARTICLE_DETAIL
)
public class ArticleDetailAct extends BaseAct<ArticleDetailViewModel> {

    MainArticleDetailActBinding mBinding;

    @FiledAutowiredAnno("articleId")
    String articleId;

    @Nullable
    @Override
    protected Class<? extends ArticleDetailViewModel> getViewModelClass() {
        return ArticleDetailViewModel.class;
    }

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

        subscibeUi(mViewModel.subscribeArticleDetail(), item -> {

            final Markwon markwon = Markwon.builder(mContext)
                    // automatically create Glide instance
                    .usePlugin(GlideImagesPlugin.create(mContext))
                    // use supplied Glide instance
                    .usePlugin(GlideImagesPlugin.create(Glide.with(mContext)))
                    // if you need more control
                    .usePlugin(GlideImagesPlugin.create(new GlideImagesPlugin.GlideStore() {
                        @NonNull
                        @Override
                        public RequestBuilder<Drawable> load(@NonNull AsyncDrawable drawable) {
                            return Glide.with(mContext).load(drawable.getDestination());
                        }

                        @Override
                        public void cancel(@NonNull Target<?> target) {
                            Glide.with(mContext).clear(target);
                        }
                    }))
                    .usePlugin(new AbstractMarkwonPlugin() {
                        @Override
                        public void configureTheme(@NonNull MarkwonTheme.Builder builder) {
                            builder
                                    .codeTextColor(Color.BLACK)
                                    .codeBackgroundColor(Color.GREEN);
                        }
                    })
                    .build();

            markwon.setMarkdown(mBinding.tvContent, item.getContent());
        });

        mViewModel.loadArticle(articleId);

    }

}
