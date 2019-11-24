package com.iknow.module.help.service;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.iknow.module.base.service.help.SmsService;
import com.iknow.module.base.support.ServiceException;
import com.xiaojinzi.component.anno.ServiceAnno;

import cn.smssdk.SMSSDK;
import io.reactivex.Completable;

@ServiceAnno(SmsService.class)
public class SmsServiceImpl implements SmsService {

    @NonNull
    @Override
    public Completable sendSms(@NonNull String phone) {
        if (TextUtils.isEmpty(phone)) {
            return Completable.error(new ServiceException("手机号码不能为空"));
        }
        return Completable.fromAction(() -> SMSSDK.getVerificationCode("86", phone));
    }

}
