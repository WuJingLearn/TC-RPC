package org.javaboy.tcrpc.server.process.handler;

import io.netty.channel.ChannelHandlerContext;
import org.javaboy.tcrpc.codec.packet.Header;
import org.javaboy.tcrpc.codec.packet.Request;
import org.javaboy.tcrpc.codec.packet.Response;
import org.javaboy.tcrpc.codec.packet.TCProtocol;
import org.javaboy.tcrpc.rpc.service.ServiceManger;
import org.javaboy.tcrpc.rpc.service.ServiceProvider;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author:majin.wj
 */
public class RequestHandler {

    private static ThreadPoolExecutor BUSINESS_EXECUTOR = ((ThreadPoolExecutor) Executors.newFixedThreadPool(Integer.MAX_VALUE));

    public static void handleRequest(ChannelHandlerContext ctx, TCProtocol protocol) {
        BUSINESS_EXECUTOR.execute(() -> {
            Header requestHeader = protocol.getHeader();
            Request request = (Request) protocol.getBody();

            String serviceKey = request.getServiceKey();
            String methodName = request.getMethodName();
            Class<?>[] argTypes = request.getArgTypes();
            Object[] args = request.getArgs();


            ServiceManger serviceManger = ServiceManger.DEFAULT;
            ServiceProvider provider = serviceManger.getServiceProvider(serviceKey);
            CompletableFuture<Object> retFuture = provider.invoke(methodName, argTypes, args);

            TCProtocol respProtocol = new TCProtocol();
            Header responseHeader = new Header(requestHeader);
            responseHeader.setRequestType(TCProtocol.RESPONSE);
            Response response = new Response();
            respProtocol.setBody(response);
            respProtocol.setHeader(responseHeader);

            Class<?> returnType = provider.getReturnType(methodName, argTypes);

            retFuture.whenComplete((r, e) -> {
                if (e != null) {
                    response.setSuccess(false);
                    response.setErrorMsg(e.getMessage());
                } else {
                    response.setSuccess(true);
                    response.setRetValue(r);
                    response.setRetType(returnType);
                }
                ctx.writeAndFlush(respProtocol);
            });

        });
    }

}
