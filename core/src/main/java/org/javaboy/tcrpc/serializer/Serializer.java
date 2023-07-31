package org.javaboy.tcrpc.serializer;

import org.javaboy.tcrpc.exception.RpcException;

/**
 * @author:majin.wj
 */
public interface Serializer {

    byte[] serialize(Object data) throws RpcException;

    <T> T deSerialize(byte[] content, Class<T> type) throws RpcException;

}
