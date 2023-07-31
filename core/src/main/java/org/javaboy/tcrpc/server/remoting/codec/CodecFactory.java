package org.javaboy.tcrpc.server.remoting.codec;

import org.javaboy.tcrpc.exception.RpcException;
import org.javaboy.tcrpc.server.remoting.codec.impl.CodecV1;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:majin.wj
 */
public class CodecFactory {

    public static Map<Byte, Codec> CODEC_MAP = new HashMap<>();

    public static Codec getCodec(byte type) {
        return CODEC_MAP.computeIfAbsent(type, (k) -> {
            if (type == CodecV1.type) {
                return new CodecV1();
            } else {
                throw new RpcException("codec type:" + type + "not support");
            }
        });
    }

}
