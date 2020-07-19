package com.iknow.module.base.service.help;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.help.NetWorkLogInfoBean;

public interface NetworkLogService {

    /**
     * 发送网络数据
     */
    void send(@NonNull NetWorkLogInfoBean netWorkLogInfo);

}
