package com.iknow.module.help.service;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.iknow.lib.beans.help.NetWorkLogInfoBean;
import com.iknow.module.base.service.help.NetworkLogService;
import com.xiaojinzi.component.anno.ServiceAnno;
import com.xiaojinzi.component.service.IServiceLifecycle;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

@ServiceAnno(NetworkLogService.class)
public class NetworkLogServiceImpl implements NetworkLogService, IServiceLifecycle {

    private Gson g = new Gson();

    private WebSocket mWebSocket;

    /*服务端分配的唯一 tag*/
    private String tag;

    private OkHttpClient httpClient = new OkHttpClient.Builder().build();

    @Override
    public void onCreate(@NonNull Application app) {
        startCocketConnect();
    }

    private void startCocketConnect(){
        if (mWebSocket != null) {
            return;
        }
        Request request = new Request.Builder().url("ws://xiaojinzi.tpddns.cn:18080/networkLog/networkProvide")
                .build();
        new OkHttpClient.Builder()
                .build()
                .newWebSocket(request, new WebSocketListener() {
                    @Override
                    public void onOpen(WebSocket webSocket, Response response) {
                        super.onOpen(webSocket, response);
                        mWebSocket = webSocket;
                        MessageBean messageBean = MessageBean.deviceNameBuild("iknow");
                        webSocket.send(g.toJson(messageBean));
                    }

                    @Override
                    public void onClosing(WebSocket webSocket, int code, String reason) {
                        super.onClosing(webSocket, code, reason);
                        destroyWebSocket();
                    }

                    @Override
                    public void onMessage(WebSocket webSocket, String text) {
                        super.onMessage(webSocket, text);
                        MessageBean messageBean = g.fromJson(text, MessageBean.class);
                        if (MessageBean.TAG_FLAG.equals(messageBean.getAction())) {
                            tag = messageBean.getData().toString();
                        }
                    }

                    @Override
                    public void onClosed(WebSocket webSocket, int code, String reason) {
                        super.onClosed(webSocket, code, reason);
                        destroyWebSocket();
                    }
                });
    }

    @Override
    public void onDestroy() {
        mWebSocket = null;
    }

    private void destroyWebSocket() {
        mWebSocket = null;
        tag = null;
    }

    @Override
    public void send(@NonNull NetWorkLogInfoBean netWorkLogInfo) {
        startCocketConnect();
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        // 通过 http 发, socket 发送不了大数据.
        MessageBean messageBean = MessageBean.networkBuild(netWorkLogInfo);
        FormBody formBody = new FormBody.Builder()
                .add("data", g.toJson(messageBean))
                .add("tag", tag)
                .build();
        Request request = new Request.Builder()
                .url("http://xiaojinzi.tpddns.cn:18080/networkLog/network/log")
                .post(formBody)
                .build();
        httpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // empty
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // empty
                    }
                });
    }

}
