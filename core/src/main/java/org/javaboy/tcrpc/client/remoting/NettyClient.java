package org.javaboy.tcrpc.client.remoting;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.java.Log;
import org.javaboy.tcrpc.client.remoting.connection.Connection;
import org.javaboy.tcrpc.client.remoting.handler.RpcClientHandler;
import org.javaboy.tcrpc.codec.RpcDecoder;
import org.javaboy.tcrpc.codec.RpcEncoder;
import org.javaboy.tcrpc.codec.packet.TCProtocol;
import org.javaboy.tcrpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:majin.wj
 */
public class NettyClient implements Client {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClient.class);

    private String ip;
    private Integer port;
    private Channel channel;
    private Connection connection;

    public NettyClient(String ip, Integer port, Connection connection) {
        this.ip = ip;
        this.port = port;
        this.connection = connection;
    }

    @Override
    public void connect() throws RpcException {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap().group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("encoder", new RpcEncoder());
                    pipeline.addLast("decoder", new RpcDecoder());
                    pipeline.addLast("handler", new RpcClientHandler(connection.getResponseHandler()));

                }
            });

            ChannelFuture future = bootstrap.connect(this.ip, this.port).sync();
            this.channel = future.channel();
            LOG.info("Netty Client connect success ..");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void close() throws RpcException {
        channel.close();
    }

    @Override
    public void sendData(TCProtocol protocol) throws RpcException {
        channel.writeAndFlush(protocol);
    }
}
