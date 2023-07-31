package org.javaboy.tcrpc.rpc.service;

import java.util.*;

/**
 * @author:majin.wj
 */
public class ServiceManger {

    public static final ServiceManger DEFAULT = new ServiceManger();

    private ServiceManger() {

    }

    private List<String> serviceBeanNames = new ArrayList<>();

    /**
     * key:serviceKey
     * value: 服务描述信息
     */
    private Map<String, ServiceDescriptor> serviceDescriptorMap = new HashMap<>();

    private Map<String, ServiceProvider> serviceProviderMap = new HashMap<>();


    public void addServiceBeanName(String beanName) {
        serviceBeanNames.add(beanName);
    }

    public List<String> getServiceBeanNames() {
        return Collections.unmodifiableList(serviceBeanNames);
    }

    public void addServiceDescriptor(String serviceKey, ServiceDescriptor descriptor) {
        serviceDescriptorMap.put(serviceKey, descriptor);
    }

    public void addServiceProvider(String serviceKey, ServiceProvider provider) {
        serviceProviderMap.put(serviceKey, provider);
    }

}
