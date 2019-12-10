package com.iknow.module.main.module.common.url.view;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iknow.lib.beans.main.CommonUrlBean;
import com.iknow.module.main.R;

public class CommonUrlAdapter extends BaseQuickAdapter<CommonUrlBean, BaseViewHolder> {

    public CommonUrlAdapter() {
        super(R.layout.main_common_url_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CommonUrlBean item) {
        TextView tv_desc = helper.getView(R.id.tv_desc);
        tv_desc.setText(item.getDesc());
    }

}
