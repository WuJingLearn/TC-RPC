package org.javaboy.tcrpc.rpc.service;

/**
 * @author:majin.wj
 */
public class ServiceProvider {

    private Object serviceBean;

    private String serviceKey;

    public ServiceProvider(Object serviceBean) {
        this.serviceBean = serviceBean;
    }

    public void invoke(){}



}
