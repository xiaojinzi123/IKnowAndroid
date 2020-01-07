package com.iknow.module.base.view;

public class BusinessError {

    private int type;

    private String msg;

    public static BusinessError build(int type) {
        BusinessError businessError = new BusinessError();
        businessError.setType(type);
        return businessError;
    }

    public static BusinessError build(int type, String msg) {
        BusinessError businessError = new BusinessError();
        businessError.setType(type);
        businessError.setMsg(msg);
        return businessError;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
