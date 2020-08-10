package com.iknow.module.help.service;

import com.iknow.lib.beans.help.NetWorkLogInfoBean;

/**
 * app 和 web 通信的格式,服务器通过其中"targetAppTag"和"targetWebTag"找出目标设备,发送过去
 * time   : 2018/05/20
 *
 * @author : xiaojinzi 30212
 */
public class MessageBean<T> {

    /**
     * 真正的数据的key
     */
    public static final String DATA_FLAG = "data";

    /**
     * action的key
     */
    public static final String ACTION_FLAG = "action";

    /**
     * 自身的tag的key
     */
    public static final String SELF_FLAG = "selfTag";

    /**
     * 设备列表 key
     */
    public static final String DEVICES_FLAG = "deviceList";

    /**
     * 设置设备名称的 key
     */
    public static final String DEVICE_NAME_FLAG = "deviceName";

    // 表示原始拦截器
    public static final String NETWORK_FLAG = "network";
    // 处理过的, 基本表示最后一个日志拦截器
    public static final String NETWORK_PROCESSED_FLAG = "networkProcessed";
    public static final String TAG_FLAG = "tag";

    // 表示自身的tag
    private String selfTag;

    // 表示需要支持的操作
    private String action;

    // 真正发送出去的数据,最终会转化成 json 数据传出去
    private T data;

    public MessageBean() {
    }

    public MessageBean(String selfTag, String action, T data) {
        this.selfTag = selfTag;
        this.action = action;
        this.data = data;
    }

    public static MessageBean deviceNameBuild(String name) {
        MessageBean result = new MessageBean();
        result.setAction(DEVICE_NAME_FLAG);
        result.setData(name);
        return result;
    }

    public static MessageBean networkBuild(NetWorkLogInfoBean netWorkLogInfo) {
        MessageBean result = new MessageBean();
        result.setAction(NETWORK_FLAG);
        result.setData(netWorkLogInfo);
        return result;
    }

    public static MessageBean networkProcessedBuild(NetWorkLogInfoBean netWorkLogInfo) {
        MessageBean result = new MessageBean();
        result.setAction(NETWORK_PROCESSED_FLAG);
        result.setData(netWorkLogInfo);
        return result;
    }

    public String getSelfTag() {
        return selfTag;
    }

    public void setSelfTag(String selfTag) {
        this.selfTag = selfTag;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}