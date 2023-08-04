package org.javaboy.tcrpc.util;

/**
 * @author:majin.wj
 */
public class ServiceKeyHelper {

    public static String serviceKey(Class<?> interfaceClass, String version) {
        if (version == null) {
            return interfaceClass.getName();
        }
        return interfaceClass.getName() + ":" + version;
    }

}
