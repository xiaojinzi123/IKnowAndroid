package com.iknow.module.datasource.network;

/**
 * @author Bacchus
 * @desc http响应体
 * @date: 2019.04.28
 */
public class Result<T> {

    private static final int SUCCESS_CODE = 0;

    private Integer errorCode;
    private String errorMsg;
    private T data;

    public Result(Integer errorCode, String errorMsg, T data) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T t) {
        return new Result(SUCCESS_CODE, null, t);
    }

    public static Result error(int errorCode, String message) {
        return new Result(errorCode, message, null);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return errorCode != null && errorCode == SUCCESS_CODE;
    }

}

