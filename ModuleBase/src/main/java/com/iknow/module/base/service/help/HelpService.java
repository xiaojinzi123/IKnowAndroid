package com.iknow.module.base.service.help;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.help.SmsCheckBean;

import io.reactivex.Single;

public interface HelpService {

    /**
     * 手机号验证
     *
     * @param context 手机号
     */
    @NonNull
    Single<SmsCheckBean> phoneCheck(@NonNull Activity context);

}
