package org.javaboy.tcrpc.rpc.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:majin.wj
 */
public class ServiceDescriptor {

    private Class<?> interfaceClass;
    private Map<String, Method> methodMap = new HashMap<>();

    public ServiceDescriptor(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;

        Method[] declaredMethods = interfaceClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            methodMap.put(method.getName(), method);
        }
    }


    public Method getMethod(String name) {
        return methodMap.get(name);
    }

}
