package com.iknow.module.help.service;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.iknow.module.base.service.help.SmsService;
import com.iknow.module.base.support.ServiceException;
import com.iknow.module.help.SystemTargetRouter;
import com.xiaojinzi.component.anno.ServiceAnno;

import cn.smssdk.SMSSDK;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

@ServiceAnno(SmsService.class)
public class SmsServiceImpl implements SmsService {

    @NonNull
    @Override
    public Completable sendSms(@NonNull String phone) {
        if (TextUtils.isEmpty(phone)) {
            return Completable.error(new ServiceException("手机号码不能为空"));
        }
        return Completable
                .fromAction(() -> SMSSDK.getVerificationCode("86", phone))
                .subscribeOn(AndroidSchedulers.mainThread());

        /*return Completable.fromAction(() -> {
            RegisterPage page = new RegisterPage();
            //如果使用我们的ui，没有申请模板编号的情况下需传null
            page.setTempCode(null);
            page.setRegisterCallback(new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // 处理成功的结果
                        HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                        // 国家代码，如“86”
                        String country = (String) phoneMap.get("country");
                        // 手机号码，如“13800138000”
                        String phone = (String) phoneMap.get("phone");
                        // TODO 利用国家代码和手机号码进行后续的操作
                    } else{
                        // TODO 处理错误的结果
                    }
                }
            });
            page.show(ToolsConfig.getApp());
        });*/

    }

}
