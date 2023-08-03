package org.javaboy.tcrpc.exception;

/**
 * @author:majin.wj
 */
public class RpcExecuteException extends RpcException{


    public RpcExecuteException(String errorMsg) {
        super(errorMsg);
    }

    public RpcExecuteException(String errorMsg, Throwable t) {
        super(errorMsg, t);
    }
}
