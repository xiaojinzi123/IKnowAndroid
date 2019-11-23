package com.iknow.lib.beans;

import com.iknow.lib.beans.user.UserInfoBean;

public class LoginBean {

    private String token;
    private String userName;

    private UserInfoBean userInfo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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
