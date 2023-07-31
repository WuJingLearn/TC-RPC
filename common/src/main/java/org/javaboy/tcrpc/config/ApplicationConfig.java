package org.javaboy.tcrpc.config;

/**
 * @author:majin.wj
 */
public class ApplicationConfig {

    public static final ApplicationConfig DEFAULT = new ApplicationConfig();

    private RegistryConfig registryConfig;

    private ProtocolConfig protocolConfig;


    public void setRegistryConfig(RegistryConfig config) {
        this.registryConfig = config;
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setProtocolConfig(ProtocolConfig protocolConfig) {
        this.protocolConfig = protocolConfig;
    }

    public ProtocolConfig getProtocolConfig() {
        return protocolConfig;
    }
}
