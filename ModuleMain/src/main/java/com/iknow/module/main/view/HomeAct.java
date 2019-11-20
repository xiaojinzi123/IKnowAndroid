package com.iknow.module.main.view;

import android.view.Gravity;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.iknow.module.base.FragmentInfo;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.service.user.UserInfoMenuService;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.main.R;
import com.iknow.module.main.databinding.MainHomeActBinding;
import com.xiaojinzi.component.anno.RouterAnno;
import com.xiaojinzi.component.impl.Router;
import com.xiaojinzi.component.impl.service.ServiceManager;

/**
 * 主界面
 */
@RouterAnno(
        path = ModuleInfo.Main.HOME
        // interceptorNames = InterceptorInfo.USER_LOGIN
)
public class HomeAct extends BaseAct {

    private MainHomeActBinding mBinding;

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.main_home_act);
        return mBinding.getRoot();
    }

    @Override
    protected void onInit() {
        super.onInit();
        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setNavigationOnClickListener(v -> mBinding.drawerLayout.openDrawer(Gravity.LEFT));

        // 拿到 HomeFragment
        Fragment homeFragment = Router.with(FragmentInfo.Main.HOME).navigate();
        if (homeFragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(mBinding.fl.getId(), homeFragment);
            ft.commit();
        }

        // 可能为空
        UserInfoMenuService userInfoMenuService = ServiceManager.get(UserInfoMenuService.class);
        if (userInfoMenuService != null) {

        }

    }

}
