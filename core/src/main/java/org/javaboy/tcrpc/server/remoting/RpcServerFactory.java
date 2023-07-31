package org.javaboy.tcrpc.server.remoting;

/**
 * @author:majin.wj
 */
public class RpcServerFactory {

    public static RpcServer createServer(String ip, Integer port) {
        return new NettyRpcServer(ip, port);
    }

}
