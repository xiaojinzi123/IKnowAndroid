package com.iknow.module.base.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SubscribeTipAnno {

    /**
     * 是否订阅提示
     */
    boolean value() default true;

}
