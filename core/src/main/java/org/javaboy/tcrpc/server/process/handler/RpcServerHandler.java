package org.javaboy.tcrpc.server.process.handler;

import io.netty.channel.ChannelHandlerContext;

import io.netty.channel.SimpleChannelInboundHandler;
import org.javaboy.tcrpc.codec.packet.Header;
import org.javaboy.tcrpc.codec.packet.Request;
import org.javaboy.tcrpc.codec.packet.Response;
import org.javaboy.tcrpc.codec.packet.TCProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:majin.wj
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<TCProtocol> {

    private static Logger LOG = LoggerFactory.getLogger(RpcServerHandler.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("receive a connection,remoteAddress:{}", ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TCProtocol protocol) throws Exception {
        Header header = protocol.getHeader();
        Request request = (Request) protocol.getBody();
        try {
            RequestHandler.handleRequest(ctx,protocol);
        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(false);
            response.setErrorMsg("request execute error,msg:" + e.getMessage());
            ctx.writeAndFlush(response);
        }

    }
}
