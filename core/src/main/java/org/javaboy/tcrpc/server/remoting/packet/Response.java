package org.javaboy.tcrpc.server.remoting.packet;

/**
 * @author:majin.wj
 */
public class Response {

    private boolean success;

    private Object retValue;

    private Class<?> retType;

    private String errorMsg;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getRetValue() {
        return retValue;
    }

    public void setRetValue(Object retValue) {
        this.retValue = retValue;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setRetType(Class<?> retType) {
        this.retType = retType;
    }

    public Class<?> getRetType() {
        return retType;
    }
}
