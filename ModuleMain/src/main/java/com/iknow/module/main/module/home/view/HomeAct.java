package com.iknow.module.main.module.home.view;

import android.os.Process;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.iknow.lib.tools.ResourceUtil;
import com.iknow.module.base.FragmentInfo;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.view.BaseAct;
import com.iknow.module.base.view.Tip;
import com.iknow.module.main.R;
import com.iknow.module.main.databinding.MainHomeActBinding;
import com.xiaojinzi.component.anno.RouterAnno;
import com.xiaojinzi.component.impl.Router;

/**
 * 主界面
 */
@RouterAnno(
        path = ModuleInfo.Main.HOME
)
public class HomeAct extends BaseAct {

    private MainHomeActBinding mBinding;

    @Override
    protected View getLayoutView() {
        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.main_home_act,
                null, false
        );
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

    }

    private int keyBackCount = 0;
    private long lastKeyBackTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBinding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mBinding.drawerLayout.closeDrawer(Gravity.LEFT);
            }else {
                if (keyBackCount == 0 || System.currentTimeMillis() - lastKeyBackTime > 2 * 1000) {
                    keyBackCount = 0;
                    keyBackCount++;
                    lastKeyBackTime = System.currentTimeMillis();
                    mView.tip(Tip.normal(ResourceUtil.getString(R.string.resource_please_press_again_to_exit_app)));
                }else {
                    finish();
                    Process.killProcess(Process.myPid());
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
