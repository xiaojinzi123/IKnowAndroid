package com.iknow.module.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iknow.module.base.FragmentInfo;
import com.iknow.module.base.view.BaseFragment;
import com.iknow.module.base.view.Tip;
import com.iknow.module.main.R;
import com.iknow.module.main.adapter.HomeAdapter;
import com.iknow.module.main.vm.HomeViewModel;
import com.xiaojinzi.component.anno.FragmentAnno;

@FragmentAnno(FragmentInfo.Main.HOME)
public class HomeFragment extends BaseFragment<HomeViewModel> {

    RecyclerView rv;

    @Nullable
    @Override
    protected Class<? extends HomeViewModel> viewModelClass() {
        return HomeViewModel.class;
    }

    @Override
    public View getLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_home_frag, null);
    }

    @Override
    protected void injectView(View contentView) {
        super.injectView(contentView);
        rv = contentView.findViewById(R.id.rv);
    }

    @Override
    protected void onInit() {
        super.onInit();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        subscibe(mViewModel.getArticleSubject(), articleBeans -> {
            HomeAdapter homeAdapter = new HomeAdapter(articleBeans);
            homeAdapter.setOnItemClickListener(
                    (adapter, view, position) -> {
                        mView.tip(Tip.normal("提示：" + position));
                    });
            rv.setAdapter(homeAdapter);
        });

    }
}
