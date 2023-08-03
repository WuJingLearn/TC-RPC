package org.javaboy.tcrpc.client.response;

import org.javaboy.tcrpc.codec.packet.Header;
import org.javaboy.tcrpc.codec.packet.Response;
import org.javaboy.tcrpc.exception.RpcExecuteException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:majin.wj
 */
public class ResponseHandler {

    public Map<Long, CompletableFuture<Object>> futureMap = new ConcurrentHashMap<>();

    public void putFuture(Long id, CompletableFuture<Object> future) {
        futureMap.put(id, future);
    }

    public void receiveResponse(Header header, Response response) {
        long id = header.getId();
        CompletableFuture<Object> future = futureMap.get(id);
        if (future != null) {
            if (response.isSuccess()) {
                future.complete(response.getRetValue());
            } else {
                future.completeExceptionally(new RpcExecuteException(response.getErrorMsg()));
            }
        }

    }

}
