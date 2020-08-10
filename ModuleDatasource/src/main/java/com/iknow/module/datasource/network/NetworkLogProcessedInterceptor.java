package com.iknow.module.datasource.network;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.iknow.lib.beans.help.NetWorkLogInfoBean;
import com.iknow.module.base.service.help.NetworkLogService;
import com.xiaojinzi.component.impl.service.ServiceManager;

/**
 * 这个类不能删除, 因为字节码会创建这个类的
 */
@Keep
public class NetworkLogProcessedInterceptor extends BaseNetworkLogInterceptor {

    @Override
    protected void doSend(@NonNull NetWorkLogInfoBean netWorkLogInfo) {
        ServiceManager.get(NetworkLogService.class)
                .sendProcessed(netWorkLogInfo);
    }

}
