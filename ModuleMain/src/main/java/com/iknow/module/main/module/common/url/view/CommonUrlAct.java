package com.iknow.module.main.module.common.url.view;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.iknow.lib.beans.main.CommonUrlBean;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.main.R;
import com.iknow.module.main.databinding.MainCommonUrlActBinding;
import com.iknow.module.main.module.common.url.vm.CommonUrlViewModel;
import com.xiaojinzi.component.anno.RouterAnno;
import com.xiaojinzi.component.impl.Router;

@RouterAnno(
        path = ModuleInfo.Main.COMMON_URL
)
public class CommonUrlAct extends BaseAct<CommonUrlViewModel> {

    private MainCommonUrlActBinding mBinding;

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.main_common_url_act,
                null, false
        );
        return mBinding.getRoot();
    }

    @Nullable
    @Override
    protected Class<? extends CommonUrlViewModel> getViewModelClass() {
        return CommonUrlViewModel.class;
    }

    @Override
    protected void onInit() {
        super.onInit();

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.rv.setLayoutManager(manager);
        CommonUrlAdapter adapter = new CommonUrlAdapter();
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (position < 0) {
                return;
            }
            CommonUrlBean commonUrlBean = adapter.getData().get(position);
            Router.with(mContext)
                    .url(commonUrlBean.getUrl())
                    .forward();
        });
        mBinding.rv.setAdapter(adapter);

        subscibeUi(mViewModel.subscribeCommonUrlObservable(), result -> {
            adapter.setNewData(result);
        });

    }

}
