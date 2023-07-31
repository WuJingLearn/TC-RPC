package org.javaboy.tcrpc.registry.impl;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.javaboy.tcrpc.config.RegistryConfig;
import org.javaboy.tcrpc.exception.RpcException;
import org.javaboy.tcrpc.registry.RegistryService;
import org.javaboy.tcrpc.registry.ServiceInstance;

/**
 * @author:majin.wj
 */
public class NacosRegistryService implements RegistryService {

    private NamingService namingService;

    public NacosRegistryService(RegistryConfig config) {
        initNacos(config.getIp(),config.getPort());
    }

    @Override
    public void register(ServiceInstance instance) {
        String serviceKey = instance.getServiceKey();
        String ip = instance.getIp();
        Integer port = instance.getPort();
        try {
            namingService.registerInstance(serviceKey, ip, port);
        } catch (NacosException e) {
            throw new RpcException("register service: " + serviceKey + "error", e);
        }
    }

    private void initNacos(String ip, String port) {
        String address = ip + ":" + port;
        try {
            this.namingService = NacosFactory.createNamingService(address);
        } catch (NacosException e) {
            throw new RpcException("init nacos register center error", e);
        }
    }
}
