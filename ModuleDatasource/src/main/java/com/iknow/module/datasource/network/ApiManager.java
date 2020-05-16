package com.iknow.module.datasource.network;

import com.iknow.module.datasource.network.apis.IknowApi;
import com.iknow.module.datasource.network.convert.IknowConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static final Object localObject = new Object(){};

    private static ApiManager apiManager;

    IknowApi iknowApi;

    public ApiManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new AuthorizationInterceptor())
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build()
                )
                .baseUrl("http://xiaojinzi.tpddns.cn:18080/iknow/")
                .addConverterFactory(IknowConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        iknowApi = retrofit.create(IknowApi.class);
    }

    public static synchronized ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public IknowApi getIknowApi() {
        return iknowApi;
    }

    public void destory(){
        synchronized (localObject) {
            apiManager = null;
            iknowApi = null;
        }
    }


}
