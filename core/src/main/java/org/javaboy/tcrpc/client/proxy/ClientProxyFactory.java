package org.javaboy.tcrpc.client.proxy;

import java.lang.reflect.Proxy;

/**
 * @author:majin.wj
 */
public class ClientProxyFactory {

    public static <T> T getProxy(Class<T> interfaceClass, String serviceKey) {
        Class<T>[] interfaces = new Class[]{interfaceClass};

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), interfaces, new RpcInvocationHandler(interfaceClass, serviceKey, null));
    }

}
