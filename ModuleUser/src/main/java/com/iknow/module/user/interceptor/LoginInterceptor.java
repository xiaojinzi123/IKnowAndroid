package com.iknow.module.user.interceptor;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.iknow.module.base.InterceptorInfo;
import com.iknow.module.base.ModuleInfo;
import com.iknow.module.base.service.user.UserService;
import com.xiaojinzi.component.anno.InterceptorAnno;
import com.xiaojinzi.component.error.ServiceNotFoundException;
import com.xiaojinzi.component.impl.Router;
import com.xiaojinzi.component.impl.RouterErrorResult;
import com.xiaojinzi.component.impl.RouterInterceptor;
import com.xiaojinzi.component.impl.RouterResult;
import com.xiaojinzi.component.impl.service.ServiceManager;
import com.xiaojinzi.component.support.CallbackAdapter;

import io.reactivex.disposables.Disposable;

/**
 * 这个拦截器要做的事情是什么呢？
 * <p>
 * 这个拦截器就是要在到下一个拦截器之前把登录给做了, 登录不成功就直接返回错误. 不继续下一个拦截器
 *
 * @author : xiaojinzi 30212
 */
@InterceptorAnno(InterceptorInfo.USER_LOGIN)
public class LoginInterceptor implements RouterInterceptor {

    @Override
    public void intercept(final Chain chain) throws Exception {
        final Context context = chain.request().getRawOrTopActivity();
        UserService userService = ServiceManager.get(UserService.class);
        if (userService == null) {
            chain.callback().onError(new ServiceNotFoundException("can't found UserService"));
            return;
        }
        Disposable disposable = userService.isLogin()
                .subscribe(b -> {
                    if (b) {
                        // 可以直接操作 Ui, 因为拦截器的线程在主线程
                        // Toast.makeText(context, "已经登录,正在帮您跳转", Toast.LENGTH_SHORT).show();
                        chain.proceed(chain.request());
                    } else {
                        // 可以直接操作 Ui, 因为拦截器的线程在主线程
                        // Toast.makeText(context, "目标界面需要登录,拦截器帮您跳转到登录界面登录", Toast.LENGTH_SHORT).show();
                        // 跳转到登录界面, 登录界面返回对应的 ResultCode 来标识登录的成功与否.
                        // 然后拦截器决定是否往下走
                        Router.with(context)
                                .host(ModuleInfo.User.NAME)
                                .path(ModuleInfo.User.LOGIN)
                                .requestCodeRandom()
                                // 匹配目标界面返回的 ResultCode
                                .forwardForResultCodeMatch(new CallbackAdapter() {
                                    @Override
                                    public void onSuccess(@NonNull RouterResult result) {
                                        chain.proceed(chain.request());
                                    }

                                    @Override
                                    public void onError(@NonNull RouterErrorResult errorResult) {
                                        chain.callback().onError(new Exception("login fail"));
                                    }
                                }, Activity.RESULT_OK);
                    }
                });
    }

}
