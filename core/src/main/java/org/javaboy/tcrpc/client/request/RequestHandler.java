package org.javaboy.tcrpc.client.request;


import org.javaboy.tcrpc.client.remoting.connection.Connection;
import org.javaboy.tcrpc.client.remoting.connection.ConnectionDirectory;
import org.javaboy.tcrpc.client.remoting.connection.ConnectionManager;
import org.javaboy.tcrpc.codec.impl.CodecV1;
import org.javaboy.tcrpc.codec.packet.Header;
import org.javaboy.tcrpc.codec.packet.Request;
import org.javaboy.tcrpc.codec.packet.TCProtocol;
import org.javaboy.tcrpc.exception.RpcException;
import org.javaboy.tcrpc.serializer.impl.FastJsonSerializer;

import java.util.concurrent.CompletableFuture;

/**
 * @author:majin.wj
 */
public class RequestHandler {

    public static final RequestHandler INSTANCE = new RequestHandler();


    private ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    public Object request(Invocation invocation) {
        ConnectionDirectory directory = connectionManager.getConnectionDirectory(invocation.getServiceKey());
        Connection connection = directory.getConnection();
        if (connection == null) {
            throw new RpcException("service:" + invocation.getServiceKey() + " no available provider");
        }
        long id = connection.getId();
        TCProtocol protocol = createProtocol(invocation, id);
        return syncRequest(protocol, connection);
    }

    private Object asyncRequest() {
        return null;
    }

    private Object syncRequest(TCProtocol protocol, Connection connection) {
        CompletableFuture<Object> resultFuture = new CompletableFuture<>();
        connection.getResponseHandler().putFuture(protocol.getHeader().getId(), resultFuture);
        connection.request(protocol);
        try {
            return resultFuture.get();
        } catch (Exception e) {
            throw new RpcException("rpc get result error", e);
        }
    }


    public TCProtocol createProtocol(Invocation invocation, long requestId) {
        Header header = new Header();
        header.setId(requestId);
        header.setType(CodecV1.type);
        header.setSerializeType(FastJsonSerializer.type);
        header.setRequestType(TCProtocol.REQUEST);
        Request request = new Request();
        request.setArgs(invocation.getArgs());
        request.setServiceKey(invocation.getServiceKey());
        request.setMethodName(invocation.getMethodName());
        request.setArgTypes(invocation.getArgTypes());

        TCProtocol tcProtocol = new TCProtocol();
        tcProtocol.setHeader(header);
        tcProtocol.setBody(request);
        return tcProtocol;
    }

}
