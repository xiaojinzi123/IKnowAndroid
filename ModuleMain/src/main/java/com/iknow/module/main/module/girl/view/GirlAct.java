package com.iknow.module.main.module.girl.view;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.main.R;
import com.iknow.module.main.databinding.MainGirlActBinding;
import com.iknow.module.main.module.girl.vm.GirlViewModel;
import com.xiaojinzi.component.anno.RouterAnno;
import com.xiaojinzi.component.impl.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RouterAnno(
        path = ModuleInfo.Main.GIRL
)
public class GirlAct extends BaseAct<GirlViewModel> {

    private MainGirlActBinding mBinding;

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.main_girl_act,
                null, false
        );
        return mBinding.getRoot();
    }

    @Nullable
    @Override
    protected Class<? extends GirlViewModel> getViewModelClass() {
        return GirlViewModel.class;
    }

    @Override
    protected void onInit() {
        super.onInit();

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mBinding.rv.setLayoutManager(manager);
        GirlAdapter adapter = new GirlAdapter();
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (position < 0) {
                return;
            }
            List<String> imageList = adapter.getData()
                    .stream()
                    .map(item -> item.getImageUrl())
                    .collect(Collectors.toList());
            Router.with(mContext)
                    .host(ModuleInfo.Help.NAME)
                    .path(ModuleInfo.Help.IMAGE_PREVIEW)
                    .putInt("position", position)
                    .putStringArrayList("images", new ArrayList<>(imageList))
                    .forward();
        });
        mBinding.rv.setAdapter(adapter);

        subscibeUi(mViewModel.subscribeGirlObservable(), girlBeans -> {
            adapter.setNewData(girlBeans);
        });

    }

}
