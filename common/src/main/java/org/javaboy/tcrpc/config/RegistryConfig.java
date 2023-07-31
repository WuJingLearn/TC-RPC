package org.javaboy.tcrpc.config;

import lombok.Data;

/**
 * @author:majin.wj
 */
@Data
public class RegistryConfig {

    /**
     * 注册中心类型
     */
    private String type;

    private String  ip;
    private Integer port;

}
