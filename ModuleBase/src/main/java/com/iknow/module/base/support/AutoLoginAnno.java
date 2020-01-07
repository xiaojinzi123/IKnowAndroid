package com.iknow.module.base.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示方法会自动完成登录
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
public @interface AutoLoginAnno {
}
