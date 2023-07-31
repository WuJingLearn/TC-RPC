package org.javaboy.tcrpc.registry;

import org.javaboy.tcrpc.config.RegistryConfig;
import org.javaboy.tcrpc.exception.RpcException;
import org.javaboy.tcrpc.registry.impl.NacosRegistryService;

/**
 * @author:majin.wj
 */
public class DefaultRegistryFactory implements RegistryFactory {

    @Override
    public RegistryService getRegistry(RegistryConfig config) {
        if (config.getType().equals("nacos")) {
            return new NacosRegistryService(config);
        }
        throw new RpcException("Not support register-center type: " + config.getType());
    }
}
