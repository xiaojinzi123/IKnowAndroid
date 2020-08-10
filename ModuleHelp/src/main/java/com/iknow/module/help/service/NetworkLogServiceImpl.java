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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

@ServiceAnno(NetworkLogService.class)
public class NetworkLogServiceImpl implements NetworkLogService, IServiceLifecycle, Runnable {

    // thread safe
    private List<MessageBean> queue = Collections.synchronizedList(new LinkedList<>());

    private Gson g = new Gson();

    private WebSocket mWebSocket;

    private volatile boolean isConnecting = false;

    /*服务端分配的唯一 tag*/
    private String tag;

    private OkHttpClient httpClient = new OkHttpClient.Builder().build();

    private boolean isDestroy = false;

    @Override
    public void onCreate(@NonNull Application app) {
        // 开启轮训线程
        new Thread(this).start();
        // 尝试连接
        startSocketConnect();
    }

    private void startSocketConnect(){
        if (mWebSocket != null) {
            return;
        }
        if (isConnecting) {
            return;
        }
        isConnecting = true;
        Request request = new Request.Builder().url("ws://xiaojinzi.tpddns.cn:18080/networkLog/networkProvide")
                .build();
        new OkHttpClient.Builder()
                .build()
                .newWebSocket(request, new WebSocketListener() {
                    @Override
                    public void onOpen(WebSocket webSocket, Response response) {
                        super.onOpen(webSocket, response);
                        mWebSocket = webSocket;
                        isConnecting = false;
                        MessageBean messageBean = MessageBean.deviceNameBuild("iknow");
                        webSocket.send(g.toJson(messageBean));
                    }

                    @Override
                    public void onClosing(WebSocket webSocket, int code, String reason) {
                        super.onClosing(webSocket, code, reason);
                        destroyWebSocket();
                        isConnecting = false;
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
                        isConnecting = false;
                    }
                });
    }

    @Override
    public void onDestroy() {
        mWebSocket = null;
        isDestroy = true;
    }

    private void destroyWebSocket() {
        mWebSocket = null;
        tag = null;
    }

    @Override
    public void send(@NonNull NetWorkLogInfoBean netWorkLogInfo) {
        doSend(MessageBean.networkBuild(netWorkLogInfo));
        startSocketConnect();
    }

    @Override
    public void sendProcessed(@NonNull NetWorkLogInfoBean netWorkLogInfo) {
        doSend(MessageBean.networkProcessedBuild(netWorkLogInfo));
        startSocketConnect();
    }

    private void doSend(@NonNull MessageBean messageBean) {
        queue.add(messageBean);
        startSocketConnect();
    }

    @Override
    public void run() {
        while (!isDestroy) {
            if (TextUtils.isEmpty(tag)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {
                    // ignore
                }
                continue;
            }
            if (queue.size() == 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {
                    // ignore
                }
                continue;
            }
            MessageBean messageBean = queue.remove(0);
            FormBody formBody = new FormBody.Builder()
                    .add("data", g.toJson(messageBean))
                    .add("tag", tag)
                    .build();
            Request request = new Request.Builder()
                    .url("http://xiaojinzi.tpddns.cn:18080/networkLog/network/log")
                    .header("developLog", "true")
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

}
