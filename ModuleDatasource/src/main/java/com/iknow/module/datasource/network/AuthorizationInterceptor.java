package com.iknow.module.datasource.network;

import com.iknow.module.base.service.user.UserService;
import com.xiaojinzi.component.impl.service.ServiceManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        UserService userService = ServiceManager.get(UserService.class);
        if (userService == null) {
            throw new IOException("获取用户Token和UserId失败");
        }

        Request.Builder builder = chain.request().newBuilder();

        String userId = userService.getUserInfo()
                .map(item -> item.getId())
                .map(item -> String.valueOf(item))
                .toSingle("")
                .blockingGet();
        String token = userService.getToken()
                .toSingle("")
                .blockingGet();

        builder.header("UserId", userId);
        builder.header("Authorization", token);

        return chain.proceed(builder.build());
    }
}
