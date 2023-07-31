package org.javaboy.tcrpc.registry;

import org.javaboy.tcrpc.config.RegistryConfig;

/**
 * @author:majin.wj
 */
public interface RegistryFactory {

    RegistryService getRegistry(RegistryConfig config);

}
