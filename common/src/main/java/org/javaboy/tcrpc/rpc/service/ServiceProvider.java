package org.javaboy.tcrpc.rpc.service;

import org.javaboy.tcrpc.exception.RpcException;
import org.javaboy.tcrpc.util.ReflectUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author:majin.wj
 */
public class ServiceProvider {

    private Object serviceBean;
    private String serviceKey;
    private Map<String, Map<String, Method>> allMethod = new HashMap<>();

    private Map<String, Map<String, Class<?>>> returnTypeMap = new HashMap<>();

    public ServiceProvider(Object serviceBean, Class<?> interfaceClass, String serviceKey) {
        this.serviceBean = serviceBean;
        this.serviceKey = serviceKey;
        intServiceDesc(interfaceClass);
    }

    private void intServiceDesc(Class<?> interfaceClass) {
        Method[] methods = interfaceClass.getDeclaredMethods();
        // 初始化方法信息
        for (Method method : methods) {
            String name = method.getName();
            String argTypeDesc = ReflectUtil.typesToString(method.getParameterTypes());
            Map<String, Method> methodMap = allMethod.computeIfAbsent(name, k -> new HashMap<>());
            methodMap.putIfAbsent(argTypeDesc, method);

            Map<String, Class<?>> returnMap = returnTypeMap.computeIfAbsent(name, k -> new HashMap<>());
            returnMap.put(argTypeDesc, method.getReturnType());
        }
    }

    private Method getMethod(String methodName, Class<?>[] argTypes) {
        Map<String, Method> methodMap = allMethod.get(methodName);
        if (methodName != null) {
            String argTypeDesc = ReflectUtil.typesToString(argTypes);
            return methodMap.get(argTypeDesc);
        }
        return null;
    }

    public Class<?> getReturnType(String methodName, Class<?>[] argTypes) {
        Map<String, Class<?>> returnMap = returnTypeMap.get(methodName);
        if (returnMap != null) {
            String argTypeDesc = ReflectUtil.typesToString(argTypes);
            return returnMap.get(argTypeDesc);
        }
        return null;
    }

    public CompletableFuture<Object> invoke(String methodName, Class<?>[] argTypes, Object[] args) {
        try {
            Method method = getMethod(methodName, argTypes);
            if (method == null) {
                throw new RpcException(String.format("service:%s cant find method: %s,argTypes:%s", this.serviceKey, methodName, ReflectUtil.typesToString(argTypes)));
            }
            Object ret = method.invoke(serviceBean, args);
            return wrapRetValue(ret);
        } catch (Exception e) {
            CompletableFuture<Object> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        }
    }

    public CompletableFuture<Object> wrapRetValue(Object ret) {
        if (ret instanceof CompletableFuture) {
            return ((CompletableFuture<Object>) ret);
        } else {
            CompletableFuture<Object> future = new CompletableFuture();
            future.complete(ret);
            return future;
        }
    }


}
