package com.iknow.lib.beans.user;

import androidx.annotation.Keep;

@Keep
public class LoginBean {

    private String token;
    private UserInfoBean userInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

}
