package org.javaboy.tcrpc.registry;

import lombok.Builder;
import lombok.Data;

/**
 * @author:majin.wj
 */
@Builder
public class ServiceInstance {

    private String serviceKey;
    private String  ip;
    private Integer port;



    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
