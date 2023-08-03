package org.javaboy.tcrpc.client.remoting.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.javaboy.tcrpc.client.response.ResponseHandler;
import org.javaboy.tcrpc.codec.packet.Response;
import org.javaboy.tcrpc.codec.packet.TCProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:majin.wj
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<TCProtocol> {

    private static Logger LOG = LoggerFactory.getLogger(RpcClientHandler.class);

    private ResponseHandler responseHandler;

    public RpcClientHandler(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("connection connected success, serverAddr:{}", ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TCProtocol msg) throws Exception {
        responseHandler.receiveResponse(msg.getHeader(), ((Response) msg.getBody()));
    }
}
