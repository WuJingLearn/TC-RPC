package org.javaboy.tcrpc.registry;

import org.javaboy.tcrpc.config.RegistryConfig;

/**
 * @author:majin.wj
 */
public interface RegistryFactory {

    RegistryFactory DEFAULT = new DefaultRegistryFactory();

    RegistryService getRegistry(RegistryConfig config);

}
