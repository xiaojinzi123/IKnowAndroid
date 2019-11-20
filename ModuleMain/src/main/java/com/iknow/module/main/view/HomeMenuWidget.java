package com.iknow.module.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.iknow.module.base.service.user.UserService;
import com.iknow.module.main.R;
import com.xiaojinzi.component.impl.service.ServiceManager;

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

        UserService userService = ServiceManager.get(UserService.class);

        Glide.with(context)
                .load("https://i.loli.net/2019/11/19/mwl3x6pYcU7ovhF.png")
                .into(iv_user_bg);

        Glide.with(context)
                .load("https://i.loli.net/2019/11/20/Yzr7jGO4cFltQLC.png")
                .placeholder(userService == null ? 0 : userService.getDefaultUserAvatar())
                .circleCrop()
                .into(iv_user_icon);

        tv_name.setText("HomeMenu");

    }

    private ImageView iv_user_bg;
    private ImageView iv_user_icon;
    private TextView tv_name;

}
