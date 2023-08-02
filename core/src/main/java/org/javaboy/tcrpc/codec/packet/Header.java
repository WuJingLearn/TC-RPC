package org.javaboy.tcrpc.codec.packet;

/**
 * @author:majin.wj
 */
public class Header {

    public static final int HEAD_SIZE = 19;

    private byte type;
    private byte serializeType;
    private byte requestType;
    private long id;
    private long length;

    public Header() {}

    public Header(Header header) {
        this.type = header.getType();
        this.serializeType = header.getSerializeType();
        this.id = header.getId();
    }


    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(byte serializeType) {
        this.serializeType = serializeType;
    }

    public byte getRequestType() {
        return requestType;
    }

    public void setRequestType(byte requestType) {
        this.requestType = requestType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
