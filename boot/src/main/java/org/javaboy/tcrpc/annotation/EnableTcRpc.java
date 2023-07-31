package org.javaboy.tcrpc.annotation;

import org.javaboy.tcrpc.boot.parse.TcImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author:majin.wj
 */
@Import(TcImportBeanDefinitionRegistrar.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableTcRpc {
    String[] packages();

    String registryType();

    String registryIp();

    String registryPort() ;

}
