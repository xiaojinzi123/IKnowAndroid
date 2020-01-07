package com.iknow.module.base.support;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xiaojinzi.component.support.Utils;

public class ErrorUtil {

    public static boolean isServiceException(@NonNull Throwable throwable) {
        return isCauseBy(throwable, ServiceException.class) == null ? false : true;
    }

    @Nullable
    public static String getServiceExceptionMsg(@NonNull Throwable throwable) {
        Throwable serviceException = isCauseBy(throwable, ServiceException.class);
        return serviceException == null ? null : serviceException.getMessage();
    }

    /**
     * 是否是由于某一个错误引起的, 如果是的话, 返回对应的错误对象
     */
    @Nullable
    public static Throwable isCauseBy(@NonNull Throwable throwable, @NonNull Class<? extends Throwable> clazz) {
        if (throwable.getClass() == clazz) {
            return throwable;
        }
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
            if (throwable.getClass() == clazz) {
                return throwable;
            }
        }
        return null;
    }

    /**
     * 获取真实的错误对象,有时候一个 {@link Throwable#cause} 就是自己本身,下面的代码看上去是死循环了
     * 但是 {@link Throwable#getCause()} 方法内部做了判断
     *
     * @param throwable
     * @return
     */
    public static Throwable getRealThrowable(@NonNull Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

}
