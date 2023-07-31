package org.javaboy.tcrpc.serializer;

import org.javaboy.tcrpc.exception.RpcException;
import org.javaboy.tcrpc.serializer.impl.FastJsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:majin.wj
 */
public class SerializerFactory {

    public static Map<Byte, Serializer> SERIALIZER_MAP = new HashMap<>();

    static {
        SERIALIZER_MAP.put(FastJsonSerializer.type, new FastJsonSerializer());
    }

    public static Serializer get(byte type) {
        Serializer serializer = SERIALIZER_MAP.get(type);
        if (serializer == null) {
            throw new RpcException("serializer type: " + type + " not support");
        } return serializer;
    }

}
