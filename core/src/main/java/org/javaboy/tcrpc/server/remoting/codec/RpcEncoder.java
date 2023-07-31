package org.javaboy.tcrpc.server.remoting.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.javaboy.tcrpc.server.remoting.packet.Header;
import org.javaboy.tcrpc.server.remoting.packet.TCProtocol;

/**
 * @author:majin.wj
 * channel 绑定，线程安全
 */
public class RpcEncoder extends MessageToByteEncoder<TCProtocol> {

    private Codec codec;
    @Override
    protected void encode(ChannelHandlerContext ctx, TCProtocol protocol, ByteBuf out) throws Exception {
        Header header = protocol.getHeader();
        if (codec == null) {
            codec = CodecFactory.getCodec(header.getType());
        }
        codec.encode(protocol,out);
    }
}
