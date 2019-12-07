package com.iknow.module.main.module.girl.view;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iknow.lib.beans.main.GirlBean;
import com.iknow.module.main.R;

public class GirlAdapter extends BaseQuickAdapter<GirlBean, BaseViewHolder> {

    public GirlAdapter() {
        super(R.layout.main_girl_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GirlBean item) {
        ImageView iv = helper.getView(R.id.iv);
        Glide.with(mContext)
                .load(item.getImageUrl())
                .into(iv);
    }

}
