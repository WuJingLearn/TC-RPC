package org.javaboy.tcrpc.client.remoting.connection;

import org.javaboy.tcrpc.client.remoting.NettyClient;
import org.javaboy.tcrpc.client.response.ResponseHandler;
import org.javaboy.tcrpc.codec.packet.TCProtocol;

import java.util.concurrent.atomic.AtomicLong;

public class Connection {

    public AtomicLong SEQUENCE = new AtomicLong();

    private NettyClient client;

    private ResponseHandler responseHandler = new ResponseHandler();

    public Connection(String ip, Integer port) {
        client = new NettyClient(ip, port, this);
    }

    public void init() {
        client.connect();
    }


    public long getId() {
        return SEQUENCE.getAndIncrement();
    }


    public void request(TCProtocol protocol) {
        System.out.println("connection 发送数据：" + protocol);
        client.sendData(protocol);
    }


    public ResponseHandler getResponseHandler() {
        return responseHandler;
    }

    public void destroy() {
        client.close();
    }
}