package org.javaboy.tcrpc.serializer.impl;

import com.alibaba.fastjson.JSON;
import org.javaboy.tcrpc.exception.RpcException;
import org.javaboy.tcrpc.serializer.Serializer;

import java.nio.charset.StandardCharsets;

/**
 * @author:majin.wj
 */
public class FastJsonSerializer implements Serializer {

    public static final byte type = 1;
    @Override
    public byte[] serialize(Object data) throws RpcException {
        return JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deSerialize(byte[] content, Class<T> type) throws RpcException {
        return JSON.parseObject(new String(content), type);
    }
}
