package org.javaboy.tcrpc.registry.impl;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.javaboy.tcrpc.config.RegistryConfig;
import org.javaboy.tcrpc.exception.RpcException;
import org.javaboy.tcrpc.registry.NotifyListener;
import org.javaboy.tcrpc.registry.RegistryService;
import org.javaboy.tcrpc.registry.ServiceInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:majin.wj
 */
public class NacosRegistryService implements RegistryService {

    private NamingService namingService;

    public NacosRegistryService(RegistryConfig config) {
        initNacos(config.getIp(), config.getPort());
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

    @Override
    public List<ServiceInstance> subscribe(String serviceKey, NotifyListener listener) {
        try {
            List<Instance> instances = namingService.getAllInstances(serviceKey);
            namingService.subscribe(serviceKey, new EventListener() {
                @Override
                public void onEvent(Event event) {
                    try {
                        List<Instance> serviceAllInstances = namingService.getAllInstances(serviceKey);

                    } catch (NacosException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            return covert(instances);
        } catch (NacosException e) {
            throw new RpcException("subscribe service: " + serviceKey + "error", e);
        }
    }


    private List<ServiceInstance> covert(List<Instance> instances) {
        List<ServiceInstance> serviceInstances = new ArrayList<>();
        for (Instance instance : instances) {
            ServiceInstance serviceInstance = ServiceInstance.builder().serviceKey(instance.getServiceName())
                    .ip(instance.getIp())
                    .port(instance.getPort())
                    .build();
            serviceInstances.add(serviceInstance);
        }
        return serviceInstances;
    }

    private void initNacos(String ip, Integer port) {
        String address = ip + ":" + port;
        try {
            this.namingService = NacosFactory.createNamingService(address);
        } catch (NacosException e) {
            throw new RpcException("init nacos register center error", e);
        }
    }
}
