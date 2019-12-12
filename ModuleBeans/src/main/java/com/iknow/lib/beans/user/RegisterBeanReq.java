package com.iknow.lib.beans.user;

import androidx.annotation.Keep;

@Keep
public class RegisterBeanReq {

    private String userName;
    private String password;
    private String checkCodeUid;
    private String checkCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckCodeUid() {
        return checkCodeUid;
    }

    public void setCheckCodeUid(String checkCodeUid) {
        this.checkCodeUid = checkCodeUid;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

}
