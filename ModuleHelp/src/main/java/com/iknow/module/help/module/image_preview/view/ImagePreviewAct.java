package com.iknow.module.help.module.image_preview.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.iknow.lib.tools.ResourceUtil;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.help.R;
import com.iknow.module.help.module.image_preview.vm.ImagePreviewViewModel;
import com.xiaojinzi.component.anno.FiledAutowiredAnno;
import com.xiaojinzi.component.anno.RouterAnno;

import java.util.ArrayList;

@RouterAnno(
        path = ModuleInfo.Help.IMAGE_PREVIEW
)
public class ImagePreviewAct extends BaseAct<ImagePreviewViewModel> {

    ViewPager2 vp;
    Toolbar toolbar;

    @FiledAutowiredAnno("images")
    ArrayList<String> images;

    @FiledAutowiredAnno("position")
    int position;

    Fragment[] fragments;

    @Override
    protected int getLayoutId() {
        return R.layout.help_image_preview_act;
    }

    @Nullable
    @Override
    protected Class<? extends ImagePreviewViewModel> getViewModelClass() {
        return ImagePreviewViewModel.class;
    }

    @Override
    protected void onInjectView() {
        super.onInjectView();
        vp = findViewById(R.id.vp);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onInit() {
        super.onInit();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        subscibeUi(mViewModel.subscribeImageIndexObservable(), result -> {
            toolbar.setTitle(String.format(ResourceUtil.getString(R.string.resource_page_index_of), result + 1));
        });

        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mViewModel.onPageSelect(position);
            }
        });

        fragments = new Fragment[images.size()];
        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = ImagePreviewFragment.newInstance(images.get(i));
        }

        vp.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments[position];
            }

            @Override
            public int getItemCount() {
                return fragments.length;
            }
        });

        if (position >= 0 && position < images.size()) {
            vp.setCurrentItem(position, false);
        }

    }

}
