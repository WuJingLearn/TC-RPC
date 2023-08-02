package org.javaboy.tcrpc.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author:majin.wj
 */
public class RpcInvocationHandler implements InvocationHandler {

    private Class<?> interfaceClass;
    private String serviceKey;
    private Map<String,Object> extraData;

    public RpcInvocationHandler(Class<?> interfaceClass, String serviceKey,Map<String,Object> extraData) {
        this.interfaceClass = interfaceClass;
        this.serviceKey = serviceKey;
        this.extraData = extraData;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        return null;
    }
}
