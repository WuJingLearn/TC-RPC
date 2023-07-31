package org.javaboy.tcrpc.exception;

/**
 * @author:majin.wj
 */
public class RpcException extends RuntimeException {

    public RpcException(String errorMsg) {
        super(errorMsg);
    }

    public RpcException(String errorMsg, Throwable t) {
        super(errorMsg, t);
    }

}
