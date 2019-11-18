package com.iknow.module.datasource.network.exception;

/**
 * @author Bacchus
 * @desc api业务异常 errorCode !=0
 * @date: 2019.04.28
 */
public class IKnowApiException extends RuntimeException {

    private Integer errorCode;

    public IKnowApiException(String msg) {
        super(msg);
    }

    public IKnowApiException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
