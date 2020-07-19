package com.iknow.module.datasource.network;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.help.NetWorkLogInfoBean;
import com.iknow.module.base.service.help.NetworkLogService;
import com.xiaojinzi.component.impl.service.ServiceManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class NetworkLogInterceptor implements Interceptor {

    private static final String CHARSET = "UTF-8";

    @Override
    public Response intercept(Chain chain) throws IOException {
        NetWorkLogInfoBean netWorkLogInfo = new NetWorkLogInfoBean();
        Request request = chain.request();
        readRequest(request, netWorkLogInfo, true);
        Response realResponse = chain.proceed(request);
        Response resultResponse = readResponse(realResponse, netWorkLogInfo);
        ServiceManager.get(NetworkLogService.class)
                .send(netWorkLogInfo);
        return resultResponse;
    }

    private void readRequest(@NonNull Request request, @NonNull NetWorkLogInfoBean info, boolean isReadBody) {
        String url = request.url().toString();
        info.setReq_method(request.method());
        info.setReq_url(url);
        Headers headers = request.headers();
        info.getReq_headers().clear();
        if (headers != null) {
            Set<String> names = headers.names();
            if (names != null) {
                for (String name : names) {
                    String headerValue = headers.get(name);
                    info.getReq_headers().add(name + ": " + headerValue);
                }
            }
        }
        if (!isReadBody) {
            info.setReq_body("because request body is too long,so here will not show request body");
        } else {
            try {
                RequestBody requestBody = request.body();
                if (requestBody != null) {
                    Buffer bufferedSink = new Buffer();
                    requestBody.writeTo(bufferedSink);
                    info.setReq_body(bufferedSink.readString(Charset.forName(CHARSET)));
                }
            } catch (Exception ignore) {
                // ignore
            }
        }
    }

    public static Response readResponse(@NonNull Response response, @NonNull NetWorkLogInfoBean info) throws IOException {
        info.setRes_code(response.code());
        info.setRes_message(response.message());
        Headers headers = response.headers();
        info.getRes_headers().clear();
        if (headers != null) {
            Set<String> names = headers.names();
            if (names != null) {
                for (String name : names) {
                    String headerValue = headers.get(name);
                    info.getRes_headers().add(name + ": " + headerValue);
                }
            }
        }
        ResponseBody resultResponseBody;
        ResponseBody responseBody = response.body();
        info.setRes_body(responseBody.string());
        resultResponseBody = ResponseBody.create(response.body().contentType(), info.getRes_body());
        return response.newBuilder().body(resultResponseBody).build();
    }

}
