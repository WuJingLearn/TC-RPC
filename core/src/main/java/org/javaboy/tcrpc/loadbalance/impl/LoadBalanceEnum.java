package org.javaboy.tcrpc.loadbalance.impl;

/**
 * @author:majin.wj
 */
public enum LoadBalanceEnum {

    RANDOM("random");


    private String code;

    private LoadBalanceEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
