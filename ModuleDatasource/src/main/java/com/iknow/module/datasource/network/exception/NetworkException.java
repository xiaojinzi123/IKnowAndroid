package com.iknow.module.datasource.network.exception;

import com.iknow.module.base.support.ServiceException;

/**
 * 网络业务异常
 */
public class NetworkException extends ServiceException {

    private Integer errorCode;

    public NetworkException(String msg) {
        super(msg);
    }

    public NetworkException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
