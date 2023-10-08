package org.javaboy.tcrpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author:majin.wj
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TCReference {

    String id() default "";
    String version() default "1.0.0";

    /**
     * 是否异步调用
     * @return
     */
    boolean async() default false;

    /**
     * 超时时间
     * @return
     */
    int timeout() default 3000;
}
