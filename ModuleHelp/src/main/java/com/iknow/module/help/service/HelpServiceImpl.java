package com.iknow.module.help.service;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.help.SmsCheckBean;
import com.iknow.module.base.service.help.HelpService;
import com.xiaojinzi.component.anno.ServiceAnno;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import io.reactivex.Single;

@ServiceAnno(HelpService.class)
public class HelpServiceImpl implements HelpService {

    @NonNull
    @Override
    public Single<SmsCheckBean> phoneCheck(@NonNull Activity context) {
        return Single.create(emitter -> {
            if (emitter.isDisposed()) {
                return;
            }
            RegisterPage page = new RegisterPage();
            //如果使用我们的ui，没有申请模板编号的情况下需传null
            page.setTempCode(null);
            page.setRegisterCallback(new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    if (emitter.isDisposed()) {
                        return;
                    }
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // 处理成功的结果
                        HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                        // 国家代码，如“86”
                        String country = (String) phoneMap.get("country");
                        // 手机号码，如“13800138000”
                        String phone1 = (String) phoneMap.get("phone");
                        // TODO 利用国家代码和手机号码进行后续的操作
                        emitter.onSuccess(new SmsCheckBean(country, phone1));
                    } else{
                        emitter.onError(new Exception("验证失败"));
                    }
                }
            });
            page.show(context);
        });


    }

}
