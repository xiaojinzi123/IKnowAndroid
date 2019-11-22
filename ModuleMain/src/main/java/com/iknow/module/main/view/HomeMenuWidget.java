package com.iknow.module.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.bumptech.glide.Glide;
import com.iknow.module.base.service.CommonService;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.main.R;
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

        init(context);

    }

    private ImageView iv_user_bg;
    private ImageView iv_user_icon;
    private TextView tv_name;

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
                .filter(item -> item.getTarget() == context)
                .filter(item -> item.getEvent() == Lifecycle.Event.ON_DESTROY)
                .subscribe(item -> disposables.clear())
        );

    }

}
