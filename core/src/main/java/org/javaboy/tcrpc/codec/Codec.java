package org.javaboy.tcrpc.codec;

import io.netty.buffer.ByteBuf;
import org.javaboy.tcrpc.codec.packet.TCProtocol;
import org.javaboy.tcrpc.exception.RpcException;

/**
 * @author:majin.wj
 */
public interface Codec {

    void encode(TCProtocol protocol, ByteBuf out) throws RpcException;


    TCProtocol decode(ByteBuf buf) throws RpcException;

}
