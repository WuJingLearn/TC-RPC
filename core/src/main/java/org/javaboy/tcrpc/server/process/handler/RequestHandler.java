package org.javaboy.tcrpc.server.process.handler;

import org.javaboy.tcrpc.rpc.service.ServiceManger;
import org.javaboy.tcrpc.rpc.service.ServiceProvider;
import org.javaboy.tcrpc.server.remoting.packet.Header;
import org.javaboy.tcrpc.server.remoting.packet.Request;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author:majin.wj
 */
public class RequestHandler {

    private static ThreadPoolExecutor BUSINESS_EXECUTOR = ((ThreadPoolExecutor) Executors.newFixedThreadPool(Integer.MAX_VALUE));

    public static void handleRequest(Header header, Request request) {

        BUSINESS_EXECUTOR.execute(() -> {

            String serviceKey = request.getServiceKey();
            String methodName = request.getMethodName();
            Class<?>[] argTypes = request.getArgTypes();
            Object[] args = request.getArgs();


            ServiceManger serviceManger = ServiceManger.DEFAULT;
            ServiceProvider provider = serviceManger.getServiceProvider(serviceKey);
            CompletableFuture<Object> retFuture = provider.invoke(methodName, argTypes, args);

            retFuture.whenComplete((r, e) -> {

            });

        });
    }

}
