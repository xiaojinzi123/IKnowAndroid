package com.iknow.module.main.module.article.view

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.Target
import com.iknow.lib.beans.main.ArticleDetailBean
import com.iknow.module.base.ModuleInfo
import com.iknow.module.base.view.BaseAct
import com.iknow.module.main.R
import com.iknow.module.main.databinding.MainArticleDetailActBinding
import com.iknow.module.main.module.article.vm.ArticleDetailViewModel
import com.xiaojinzi.component.anno.AttrValueAutowiredAnno
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.impl.Router
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonConfiguration
import io.noties.markwon.image.AsyncDrawable
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin.GlideStore

@RouterAnno(path = ModuleInfo.Main.ARTICLE_DETAIL)
class ArticleDetailAct : BaseAct<ArticleDetailViewModel?>() {
    var mBinding: MainArticleDetailActBinding? = null
    @JvmField
    @AttrValueAutowiredAnno("articleId")
    var articleId: String? = null

    override fun getViewModelClass(): Class<out ArticleDetailViewModel>? {
        return ArticleDetailViewModel::class.java
    }

    override fun getLayoutView(): View {
        mBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.main_article_detail_act,
            null, false
        )
        return mBinding!!.root
    }

    override fun onInit() {
        super.onInit()
        setSupportActionBar(mBinding!!.toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        subscibeUi(
            mViewModel!!.subscribeArticleDetail()
        ) { item: ArticleDetailBean ->
            val markdown = Markwon.builder(mContext) // automatically create Glide instance
                .usePlugin(GlideImagesPlugin.create(mContext)) // use supplied Glide instance
                .usePlugin(GlideImagesPlugin.create(Glide.with(mContext))) // if you need more control
                .usePlugin(GlideImagesPlugin.create(object : GlideStore {
                    override fun load(drawable: AsyncDrawable): RequestBuilder<Drawable> {
                        return Glide.with(mContext).load(drawable.destination)
                    }

                    override fun cancel(target: Target<*>) {
                        Glide.with(mContext).clear(target)
                    }
                }))
                .usePlugin(object : AbstractMarkwonPlugin() {
                    override fun configureConfiguration(builder: MarkwonConfiguration.Builder) {
                        super.configureConfiguration(builder)
                        builder.linkResolver { _: View?, link: String? ->
                            Router.with(mContext)
                                .url(link!!)
                                .forward()
                        }
                    }
                })
                .build()
            markdown.setMarkdown(mBinding!!.tvContent, item.content)
        }
        mViewModel!!.loadArticle(articleId)
    }
}