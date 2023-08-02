package org.javaboy.tcrpc.registry;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface RegistryService {
    void register(ServiceInstance instance);

    List<ServiceInstance> subscribe(String serviceKey,NotifyListener listener);
}
