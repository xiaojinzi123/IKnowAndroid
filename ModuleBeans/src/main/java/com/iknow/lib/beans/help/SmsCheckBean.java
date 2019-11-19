package com.iknow.lib.beans.help;

public class SmsCheckBean {

    private String country;
    private String phone;

    public SmsCheckBean(String country, String phone) {
        this.country = country;
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
