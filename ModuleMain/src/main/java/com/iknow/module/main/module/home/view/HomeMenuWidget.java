package com.iknow.module.main.module.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;

import com.bumptech.glide.Glide;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.service.CommonService;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.main.R;
import com.xiaojinzi.component.impl.Router;
import com.xiaojinzi.component.impl.service.RxServiceManager;
import com.xiaojinzi.component.impl.service.ServiceManager;

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
        cl_header = findViewById(R.id.cl_header);
        ll_setting = findViewById(R.id.ll_setting);
        ll_address = findViewById(R.id.ll_address);

        cl_header.setOnClickListener( view -> {
            Router.with(context)
                    .host(ModuleInfo.User.NAME)
                    .path(ModuleInfo.User.EDIT)
                    .forward();
        });

        ll_setting.setOnClickListener( view -> {
            Router.with(context)
                    .host(ModuleInfo.Main.NAME)
                    .path(ModuleInfo.Main.SETTING)
                    .forward();
        });

        ll_address.setOnClickListener( view -> {
            Router.with(context)
                    .host(ModuleInfo.Help.NAME)
                    .path(ModuleInfo.Help.ADDRESS_SELECT)
                    .forward();
        });

        init(context);

    }

    private ImageView iv_user_bg;
    private ImageView iv_user_icon;
    private TextView tv_name;
    private ConstraintLayout cl_header;
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
                                item.ifPresent(userInfo -> {
                                    tv_name.setText(userInfo.getName());
                                    Glide.with(context)
                                            .load(userInfo.getBackgroundUrl())
                                            .placeholder(userService == null ? 0 : userService.getDefaultUserBg())
                                            .into(iv_user_bg);
                                    Glide.with(context)
                                            .load(userInfo.getAvatar())
                                            .placeholder(userService == null ? 0 : userService.getDefaultUserAvatar())
                                            .circleCrop()
                                            .into(iv_user_icon);
                                });
                            })
            );
        }
        disposables.add(RxServiceManager
                .with(CommonService.class)
                .flatMapObservable(service -> service.subscribeActivityLifecy())
                // 比如是当前的 Activity
                .filter(item -> item.getTarget() == context)
                // 必须是销毁的事件
                .filter(item -> item.getEvent() == Lifecycle.Event.ON_DESTROY)
                .subscribe(item -> disposables.clear())
        );

    }

}
