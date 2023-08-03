package org.javaboy.tcprc.pojo;

/**
 * @author:majin.wj
 */


public class Response<T> {
    public T data;
    public String errorMsg;


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
