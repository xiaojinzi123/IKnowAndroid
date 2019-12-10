package com.iknow.module.main.module.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;

import com.bumptech.glide.Glide;
import com.iknow.lib.beans.user.UserInfoBean;
import com.iknow.lib.tools.ResourceUtil;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.service.CommonService;
import com.iknow.module.base.service.main.HomeMenuService;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.main.R;
import com.xiaojinzi.component.impl.Router;
import com.xiaojinzi.component.impl.service.RxServiceManager;
import com.xiaojinzi.component.impl.service.ServiceManager;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class HomeMenuWidget extends FrameLayout {

    public HomeMenuWidget(@NonNull Context context) {
        this(context, null);
    }

    public HomeMenuWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeMenuWidget(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.main_home_menu, this);

        iv_user_bg = findViewById(R.id.iv_user_bg);
        iv_user_icon = findViewById(R.id.iv_user_icon);
        tv_name = findViewById(R.id.tv_name);
        tv_sign_in = findViewById(R.id.tv_sign_in);
        cl_header = findViewById(R.id.cl_header);
        ll_common_url = findViewById(R.id.ll_common_url);
        ll_beauty_girl = findViewById(R.id.ll_beauty_girl);
        ll_setting = findViewById(R.id.ll_setting);
        ll_address = findViewById(R.id.ll_address);

        cl_header.setOnClickListener(view -> {
            Router.with(context)
                    .host(ModuleInfo.User.NAME)
                    .path(ModuleInfo.User.EDIT)
                    .afterJumpAction(() -> closeMenu())
                    .forward();
        });

        iv_user_bg.setOnClickListener(view -> {
            disposables.add(
                    RxServiceManager.with(UserService.class)
                            .flatMapMaybe(service -> service.getUserInfo())
                            .map(item -> item.getBackgroundUrl() == null ? "" : item.getBackgroundUrl())
                            .filter(item -> !item.isEmpty())
                            .subscribe(url -> {
                                Router.with(context)
                                        .host(ModuleInfo.Help.NAME)
                                        .path(ModuleInfo.Help.IMAGE_PREVIEW)
                                        .putStringArrayList("images", new ArrayList<>(Arrays.asList(url)))
                                        .afterJumpAction(() -> closeMenu())
                                        .forward();
                            })
            );
        });

        tv_name.setOnClickListener(view -> {
            disposables.add(
                    RxServiceManager.with(UserService.class)
                            .map(item -> item.isLogin())
                            .filter(item -> !item)
                            .subscribe(b -> Router
                                    .with(context)
                                    .host(ModuleInfo.User.NAME)
                                    .path(ModuleInfo.User.LOGIN)
                                    .afterJumpAction(() -> closeMenu())
                                    .forward()
                            )
            );
        });

        tv_sign_in.setOnClickListener(view -> {
            disposables.add(
                    RxServiceManager.with(UserService.class)
                            .flatMapCompletable(service -> service.signIn())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> {
                                Toast.makeText(context, "签到成功", Toast.LENGTH_SHORT).show();
                            })
            );
        });

        ll_common_url.setOnClickListener(view -> {
            Router.with(context)
                    .host(ModuleInfo.Main.NAME)
                    .path(ModuleInfo.Main.COMMON_URL)
                    .afterJumpAction(() -> closeMenu())
                    .forward();
        });

        ll_beauty_girl.setOnClickListener(view -> {
            Router.with(context)
                    .host(ModuleInfo.Main.NAME)
                    .path(ModuleInfo.Main.GIRL)
                    .afterJumpAction(() -> closeMenu())
                    .forward();
        });

        ll_setting.setOnClickListener(view -> {
            Router.with(context)
                    .host(ModuleInfo.Main.NAME)
                    .path(ModuleInfo.Main.SETTING)
                    .afterJumpAction(() -> closeMenu())
                    .forward();
        });

        ll_address.setOnClickListener(view -> {
            Router.with(context)
                    .host(ModuleInfo.Help.NAME)
                    .path(ModuleInfo.Help.ADDRESS_SELECT)
                    .afterJumpAction(() -> closeMenu())
                    .forward();
        });

        init(context);

    }

    private ImageView iv_user_bg;
    private ImageView iv_user_icon;
    private TextView tv_name;
    private TextView tv_sign_in;
    private ConstraintLayout cl_header;
    private LinearLayout ll_common_url;
    private LinearLayout ll_beauty_girl;
    private LinearLayout ll_setting;
    private LinearLayout ll_address;

    private CompositeDisposable disposables = new CompositeDisposable();

    private void init(@NonNull Context context) {
        UserService userService = ServiceManager.get(UserService.class);
        if (userService != null) {
            disposables.add(
                    userService.subscribeUser()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(item -> {
                                String userBg = null, userAvatar = null;
                                if (item.isPresent()) {
                                    UserInfoBean userInfo = item.get();
                                    tv_name.setText(userInfo.getName());
                                    userBg = userInfo.getBackgroundUrl();
                                    userAvatar = userInfo.getAvatar();
                                } else {
                                    // 登录提示
                                    tv_name.setText(ResourceUtil.getString(R.string.resource_click_to_login));
                                }
                                Glide.with(context)
                                        .load(userBg)
                                        .placeholder(userService.getDefaultUserBg())
                                        .into(iv_user_bg);
                                Glide.with(context)
                                        .load(userAvatar)
                                        .placeholder(userService.getDefaultUserAvatar())
                                        .circleCrop()
                                        .into(iv_user_icon);
                            })
            );
        }
        disposables.add(RxServiceManager
                .with(CommonService.class)
                .flatMapObservable(service -> service.subscribeActivityLifecycle())
                // 比如是当前的 Activity
                .filter(item -> item.getTarget() == context)
                // 必须是销毁的事件
                .filter(item -> item.getEvent() == Lifecycle.Event.ON_DESTROY)
                .subscribe(
                        item -> disposables.clear(),
                        error -> {
                            Toast.makeText(context, "提示：" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        })
        );

    }

    private void closeMenu() {
        ServiceManager.get(HomeMenuService.class)
                .closeMenu();
    }

}
