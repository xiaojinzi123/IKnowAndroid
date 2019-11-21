package com.iknow.lib.beans.user;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.LoginBean;

public class UserInfoBean {

    private Integer id;
    private String name;
    private String gender;

    public UserInfoBean(@NonNull LoginBean target) {
        id = target.getUserId();
        name = target.getName();
        gender = target.getGender();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
