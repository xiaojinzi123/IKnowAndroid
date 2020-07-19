package com.iknow.module.help.interceptor;

import android.net.Uri;

import com.iknow.module.base.ModuleInfo;
import com.xiaojinzi.component.anno.GlobalInterceptorAnno;
import com.xiaojinzi.component.impl.RouterInterceptor;
import com.xiaojinzi.component.impl.RouterRequest;
import com.xiaojinzi.component.support.ParameterSupport;

/**
 * 全局的一个拦截器,让网页的 schemes 跳转到网页的界面去
 * 优先级设置的高一些
 */
@GlobalInterceptorAnno(priority = 1000)
public class WebViewInterceptor implements RouterInterceptor {

    @Override
    public void intercept(Chain chain) throws Exception {
        Uri uri = chain.request().uri;
        String scheme = uri.getScheme();
        Boolean isUseSystem = ParameterSupport.getBoolean(chain.request().bundle, "isUseSystem", false);
        if (ModuleInfo.HTTP_SCHEME.equalsIgnoreCase(scheme) || ModuleInfo.HTTPS_SCHEME.equalsIgnoreCase(scheme)) {
            // 改变 request 对象路由到 网页的 Activity 去
            RouterRequest newRequest = null;
            if (isUseSystem) {
                newRequest = chain.request().toBuilder()
                        .scheme(ModuleInfo.APP_SCHEME)
                        .host(ModuleInfo.System.NAME)
                        .path(ModuleInfo.System.WEB)
                        .putString("url", uri.toString())
                        .build();
            } else {
                newRequest = chain.request().toBuilder()
                        .scheme(ModuleInfo.APP_SCHEME)
                        .host(ModuleInfo.Help.NAME)
                        .path(ModuleInfo.Help.WEB)
                        .putString("url", uri.toString())
                        .build();
            }

            // 执行
            chain.proceed(newRequest);
        } else {
            chain.proceed(chain.request());
        }
    }

}
