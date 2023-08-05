package org.javaboy.tcrpc.server.remoting;

import org.javaboy.tcrpc.exception.RpcException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author:majin.wj
 */
public interface RpcServer {

    void openServer() throws RpcException;

    void shutDown() throws RpcException;

    SocketAddress getAddress();

}
