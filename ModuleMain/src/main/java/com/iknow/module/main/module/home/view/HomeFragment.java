package com.iknow.module.main.module.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.iknow.module.base.FragmentInfo;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseFragment;
import com.iknow.module.main.R;
import com.iknow.module.main.adapter.HomeAdapter;
import com.iknow.module.main.module.home.vm.HomeViewModel;
import com.xiaojinzi.component.anno.FragmentAnno;
import com.xiaojinzi.component.impl.Router;

@FragmentAnno(FragmentInfo.Main.HOME)
public class HomeFragment extends BaseFragment<HomeViewModel> {

    SwipeRefreshLayout srl;
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
        srl = contentView.findViewById(R.id.srl);
    }

    @Override
    protected void onInit() {
        super.onInit();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        subscibe(mViewModel.subscribeRefreshObservable(), state -> {
            srl.setRefreshing(state);
        });

        subscibe(mViewModel.getArticleSubject(), articleBeans -> {
            HomeAdapter homeAdapter = new HomeAdapter(articleBeans);
            homeAdapter.setOnItemClickListener(
                    (adapter, view, position) -> {
                        if (position >= 0) {
                            String articleId = String.valueOf(
                                    homeAdapter.getData().get(position).getId()
                            );
                            Router.with(mFragment)
                                    .host(ModuleInfo.Main.NAME)
                                    .path(ModuleInfo.Main.ARTICLE_DETAIL)
                                    .putString("articleId", articleId)
                                    .forward();
                        }

                    });
            rv.setAdapter(homeAdapter);
        });

        srl.setOnRefreshListener(() -> {
            mViewModel.refresh();
        });

    }
}
