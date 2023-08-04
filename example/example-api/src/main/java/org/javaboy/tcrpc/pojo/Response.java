package org.javaboy.tcrpc.pojo;

/**
 * @author:majin.wj
 */


public class Response<T> {
    public T data;
    public String errorMsg;


    public static <T> Response of(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Response{" + "data=" + data + ", errorMsg='" + errorMsg + '\'' + '}';
    }
}
