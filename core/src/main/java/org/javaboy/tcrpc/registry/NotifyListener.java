package org.javaboy.tcrpc.registry;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface NotifyListener {
    void notify(List<ServiceInstance> instances);

}
