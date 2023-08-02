package org.javaboy.tcrpc.client.remoting;

import org.javaboy.tcrpc.codec.packet.TCProtocol;
import org.javaboy.tcrpc.exception.RpcException;

/**
 * @author:majin.wj
 */
public interface Client {

    void connect() throws RpcException;

    void close() throws RpcException;


    void sendData(TCProtocol protocol) throws RpcException;



}
