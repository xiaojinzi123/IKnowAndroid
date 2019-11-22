package com.iknow.module.base.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SubscribeLoadingAnno {

    /**
     * 是否订阅加载框
     */
    boolean value() default true;

}
