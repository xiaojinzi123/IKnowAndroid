package com.iknow.module.base;

import android.app.Application;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.xiaojinzi.component.anno.ModuleAppAnno;
import com.xiaojinzi.component.application.IComponentApplication;

@ModuleAppAnno
public class BaseModuleApplication implements IComponentApplication {

    private CommonActivityLifecycleCallback commonActivityLifecycleCallback;

    @Override
    public void onCreate(@NonNull Application app) {
        commonActivityLifecycleCallback = new CommonActivityLifecycleCallback();
        app.registerActivityLifecycleCallbacks(commonActivityLifecycleCallback);
        GlideBuilder glideBuilder =
                new GlideBuilder()
                        .setDefaultRequestOptions(
                                new RequestOptions()
                                        .placeholder(R.drawable.ui_image_placeholder_default)
                                        .fallback(R.drawable.ui_image_placeholder_default)
                                        .error(R.drawable.ui_image_error_default)
                        );
        Glide.init(app, glideBuilder);
    }

    @Override
    public void onDestroy() {
        commonActivityLifecycleCallback = null;
    }

}
