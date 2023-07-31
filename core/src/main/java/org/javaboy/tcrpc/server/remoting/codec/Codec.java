package org.javaboy.tcrpc.server.remoting.codec;

import io.netty.buffer.ByteBuf;
import org.javaboy.tcrpc.exception.RpcException;
import org.javaboy.tcrpc.server.remoting.packet.TCProtocol;

/**
 * @author:majin.wj
 */
public interface Codec {

    void encode(TCProtocol protocol, ByteBuf out) throws RpcException;


    TCProtocol decode(ByteBuf buf) throws RpcException;

}
