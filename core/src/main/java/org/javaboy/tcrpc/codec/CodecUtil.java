package org.javaboy.tcrpc.codec;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * @author:majin.wj
 */
public class CodecUtil {


    public static void writeString(String data, ByteBuf out) {
        byte[] content = data.getBytes(StandardCharsets.UTF_8);
        out.writeInt(content.length);
        out.writeBytes(content);
    }

    public static String readString(ByteBuf in) {
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);
        return new String(content);
    }

    public static void writeBytes(byte[] data, ByteBuf out) {
        out.writeInt(data.length);
        out.writeBytes(data);
    }

    public static byte[] readBytes(ByteBuf in) {
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);
        return content;
    }
}
