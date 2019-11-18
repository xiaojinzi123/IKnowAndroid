package com.iknow.module.datasource.network;

import androidx.annotation.NonNull;
import com.iknow.lib.tools.ResourceUtil;
import com.iknow.module.base.BuildConfig;
import com.iknow.module.datasource.network.convert.IknowConverterFactory;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author Bacchus
 * @date: 2019.04.28
 */
public class RetrofitFactory {

    private volatile static Retrofit retrofit;

    @NonNull
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofit == null) {
                    // 指定缓存路径,缓存大小 50Mb
                    Cache cache = new Cache(new File(ResourceUtil.getApp().getCacheDir(), "HttpCache"), 1024 * 1024 * 50);

                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .cache(cache)
                            .connectTimeout(HttpConfig.HTTP_CONNECT_TIME, TimeUnit.SECONDS)
                            .readTimeout(HttpConfig.HTTP_READ_TIME, TimeUnit.SECONDS)
                            .writeTimeout(HttpConfig.HTTP_WRITE_TIME, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true);

//                    builder.addInterceptor(InterceptorFactory.buildRequestSignHeaderInterceptor())
//                            .addInterceptor(InterceptorFactory.buildNetworkStatusInterceptor())
//                            .addInterceptor(InterceptorFactory.buildRequestPublicParametersHeaderInterceptor())
//                            .addInterceptor(InterceptorFactory.buildRequestTokenHeaderInterceptor())
//                            .addInterceptor(InterceptorFactory.buildResponseSignHeaderAuthInterceptor());

//                    if (BuildConfig.DEBUG) {
//                        // Log 拦截器 代替okhttp日志拦截器HttpLoggingInterceptor
//                        builder.addInterceptor(InterceptorFactory.buildLogInterceptor());
//
//                        // ServerLog
//                        builder.addInterceptor(new ServerLogNetworkInterceptor("network"));
//                        builder.addInterceptor(new ServerLogNetworkInterceptor("networkEncrypted"));
//                    }

                    retrofit = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .client(builder.build())
                            .addConverterFactory(IknowConverterFactory.create())
                            // TODO 加拦截器
//                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}