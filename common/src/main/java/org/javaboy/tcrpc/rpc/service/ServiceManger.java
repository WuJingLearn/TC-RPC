package org.javaboy.tcrpc.rpc.service;

import org.javaboy.tcrpc.registry.ServiceInstance;

import java.util.*;

/**
 * @author:majin.wj
 */
public class ServiceManger {

    public static final ServiceManger DEFAULT = new ServiceManger();

    private ServiceManger() {

    }

    private List<String> serviceBeanNames = new ArrayList<>();

    private List<ServiceInstance> serviceInstances = new ArrayList<>();
    /**
     * key: serviceKey
     */
    private Map<String, ServiceProvider> serviceProviderMap = new HashMap<>();


    public void addServiceBeanName(String beanName) {
        serviceBeanNames.add(beanName);
    }

    public List<String> getServiceBeanNames() {
        return Collections.unmodifiableList(serviceBeanNames);
    }

    public void addServiceProvider(String serviceKey, ServiceProvider provider) {
        serviceProviderMap.put(serviceKey, provider);
    }

    public ServiceProvider getServiceProvider(String serviceKey) {
        return serviceProviderMap.get(serviceKey);
    }

    public void addServiceInstanve(ServiceInstance instance) {
        serviceInstances.add(instance);
    }

    public List<ServiceInstance> getServiceInstances() {
        return Collections.unmodifiableList(serviceInstances);
    }
}
