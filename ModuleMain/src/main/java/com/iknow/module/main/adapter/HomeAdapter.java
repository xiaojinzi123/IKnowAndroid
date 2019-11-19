package com.iknow.module.main.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iknow.lib.beans.ArticleBean;
import com.iknow.module.main.R;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    public HomeAdapter(@Nullable List<ArticleBean> data) {
        super(R.layout.main_article_list_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleBean item) {

        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_desc = helper.getView(R.id.tv_desc);
        ImageView iv_image = helper.getView(R.id.iv_image);

        tv_title.setText(item.getTitle());
        tv_desc.setText(item.getShortDesc());
        iv_image.setVisibility(View.GONE);

        if (item.getThumbnailImageUrls() != null && item.getThumbnailImageUrls().size() > 0) {
            iv_image.setVisibility(View.VISIBLE);
            Glide.with(iv_image)
                    .load(item.getThumbnailImageUrls().get(0))
                    .into(iv_image);
        }

    }

}
