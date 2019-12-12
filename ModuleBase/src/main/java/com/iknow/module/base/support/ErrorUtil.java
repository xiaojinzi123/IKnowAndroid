package com.iknow.module.base.support;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xiaojinzi.component.support.Utils;

public class ErrorUtil {

    public static boolean isServiceException(@NonNull Throwable throwable) {
        throwable = Utils.getRealThrowable(throwable);
        if (throwable instanceof ServiceException) {
            return true;
        }
        return false;
    }

    @Nullable
    public static String getServiceExceptionMsg(@NonNull Throwable throwable) {
        throwable = Utils.getRealThrowable(throwable);
        if (throwable instanceof ServiceException) {
            return throwable.getMessage();
        }
        return null;
    }

}
