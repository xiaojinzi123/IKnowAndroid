package com.iknow.module.base.service.help;

import androidx.annotation.NonNull;

import io.reactivex.Completable;

public interface SmsService {

    /**
     * 发送手机验证码
     *
     * @param phone 手机号码
     */
    @NonNull
    Completable sendSms(@NonNull String phone);

}
