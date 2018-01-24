package com.fbee.modules.interceptor.anno;


import java.lang.annotation.*;

/**
 * 游客访问
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Guest {

}
