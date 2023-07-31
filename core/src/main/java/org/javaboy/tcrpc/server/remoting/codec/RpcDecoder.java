package org.javaboy.tcrpc.server.remoting.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.javaboy.tcrpc.server.remoting.packet.Header;
import org.javaboy.tcrpc.server.remoting.packet.TCProtocol;

import java.util.List;

/**
 * @author:majin.wj channel绑定，线程安全
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Codec codec;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < Header.HEAD_SIZE) {
            return;
        }
        if (codec == null) {
            byte type = in.getByte(in.readerIndex());
            this.codec = CodecFactory.getCodec(type);
        }
        int readerIndex = in.readerIndex();
        long bodyLength = in.getLong(readerIndex + 11);
        if (in.readableBytes() < (bodyLength+ Header.HEAD_SIZE)){
            return;
        }

     /*   byte[] data = new byte[(int)bodyLength + RequestHeader.HEAD_SIZE];
        in.readBytes(data);*/
        // todo 将一个数据包读取出来,构造单独的ByteBuf
        TCProtocol protocol = codec.decode(in);
        out.add(protocol);

    }
}
