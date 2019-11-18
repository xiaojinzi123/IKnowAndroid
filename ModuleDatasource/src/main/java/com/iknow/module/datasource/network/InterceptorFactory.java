package com.iknow.module.datasource.network;

import com.iknow.lib.tools.NetWorkUtil;
import com.iknow.lib.tools.ResourceUtil;
import com.iknow.module.datasource.network.exception.IKnowApiException;
import okhttp3.*;
import okio.Buffer;

import java.nio.charset.Charset;

/**
 * @author Bacchus
 * @desc http拦截器生成器
 * @date: 2019.04.28
 */
final class InterceptorFactory {
    /**
     * 构建quest和response日志拦截器
     */
    final static Interceptor buildLogInterceptor() {
        return chain -> {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();

            RequestBody requestBody = request.body();
            String contentReq = null;
            if (requestBody != null) {
                Buffer bufferedSink = new Buffer();
                requestBody.writeTo(bufferedSink);
                contentReq = bufferedSink.readString(Charset.forName("UTF-8"));
            }

            String contentRes = response.body().string();
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, contentRes))
                    .build();
        };
    }

    /**
     * 构建网络状态访问以及服务器连接状态拦截器
     */
    public static Interceptor buildNetworkStatusInterceptor() {
        return chain -> {
            IKnowApiException e = null;
            if (!NetWorkUtil.isNetworkConnected(ResourceUtil.getApp())) {
                throw new IKnowApiException("网络异常,请检查网络连接");
            }
            Request request = chain.request();
            Response response = chain.proceed(request);

            if (401 == response.code()) {
                e = new IKnowApiException("401");
            } else if (403 == response.code()) {
                e = new IKnowApiException("403");
            } else if (503 == response.code()) {
                e = new IKnowApiException("503");
            } else if (500 == response.code()) {
                e = new IKnowApiException("500");
            } else if (404 == response.code()) {
                e = new IKnowApiException("404");
            }
            if (null != e) {
                throw e;
            }
            return response;
        };
    }

}
