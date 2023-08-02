package org.javaboy.tcrpc.boot.parse.client;

import org.javaboy.tcrpc.client.proxy.ClientProxyFactory;
import org.springframework.beans.factory.FactoryBean;

import java.sql.Ref;

/**
 * @author:majin.wj
 */
public class ReferenceBean<T> implements FactoryBean<T> {


    private Class<T> interfaceClass;
    private String serviceKey;

    public ReferenceBean(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }


    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setServiceKey(String serviceKey) {
       this.serviceKey = serviceKey;
    }

    @Override
    public T getObject() throws Exception {
        return ClientProxyFactory.getProxy(interfaceClass,serviceKey);
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
