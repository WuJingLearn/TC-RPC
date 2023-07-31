package org.javaboy.tcrpc.rpc.config;

import lombok.Data;

/**
 * @author:majin.wj
 */
@Data
public class ServiceConfig<T> {

    private T interfaceClass;
    private String serviceName;
    private String version;

}
