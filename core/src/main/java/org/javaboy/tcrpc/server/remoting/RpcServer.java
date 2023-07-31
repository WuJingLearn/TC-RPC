package org.javaboy.tcrpc.server.remoting;

import org.javaboy.tcrpc.exception.RpcException;

/**
 * @author:majin.wj
 */
public interface RpcServer {

    void openServer() throws RpcException;

}
