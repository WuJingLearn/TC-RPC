package org.javaboy.tcrpc.config;

import lombok.Data;
import org.javaboy.tcrpc.util.NetUtil;

/**
 * @author:majin.wj desc: 协议配置，
 */
@Data
public class ProtocolConfig {

    private String ip;

    private Integer port;


    public String getIp() {
        return NetUtil.getIp();
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }
}
