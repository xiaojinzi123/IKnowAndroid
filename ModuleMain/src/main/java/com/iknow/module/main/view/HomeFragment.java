package com.iknow.module.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.iknow.module.base.FragmentInfo;
import com.iknow.module.base.view.BaseFragment;
import com.iknow.module.main.R;
import com.iknow.module.main.vm.HomeViewModel;
import com.xiaojinzi.component.anno.FragmentAnno;

@FragmentAnno(FragmentInfo.Main.HOME)
public class HomeFragment extends BaseFragment<HomeViewModel> {

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
    protected void onInit() {
        super.onInit();

        subscibe(mViewModel.getArticleSubject(), articleBeans -> {
            System.out.println("23123");
        });

    }
}
