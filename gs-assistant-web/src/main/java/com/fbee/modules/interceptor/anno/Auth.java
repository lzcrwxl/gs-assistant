package com.fbee.modules.interceptor.anno;

import java.lang.annotation.*;

/**
 * 微信授权访问
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
}
