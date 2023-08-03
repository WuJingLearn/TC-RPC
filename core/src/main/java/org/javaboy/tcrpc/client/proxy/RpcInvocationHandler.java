package org.javaboy.tcrpc.client.proxy;

import org.javaboy.tcrpc.client.request.Invocation;
import org.javaboy.tcrpc.client.request.RequestHandler;

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

        String serviceKey = this.serviceKey;
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();


        Invocation invocation = Invocation.builder().serviceKey(serviceKey)
                .methodName(methodName)
                .args(args)
                .argTypes(parameterTypes)
                .extraDatas(extraData)
                .build();

        return RequestHandler.INSTANCE.request(invocation);
    }
}
