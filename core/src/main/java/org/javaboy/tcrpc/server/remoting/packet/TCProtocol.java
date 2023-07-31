package org.javaboy.tcrpc.server.remoting.packet;

/**
 * @author:majin.wj
 */
public class TCProtocol {

    public static final byte REQUEST = 1;
    public static final byte RESPONSE = 2;

    private Header header;
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
