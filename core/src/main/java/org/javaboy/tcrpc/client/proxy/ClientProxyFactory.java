package org.javaboy.tcrpc.client.proxy;

import java.lang.reflect.Proxy;

/**
 * @author:majin.wj
 */
public class ClientProxyFactory {

    public static <T> T getProxy(Class<T> interfaceClass, String serviceKey) {
        return ((T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new RpcInvocationHandler(interfaceClass, serviceKey, null)));
    }

}
