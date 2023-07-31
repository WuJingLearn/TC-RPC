package org.javaboy.tcrpc.server.remoting;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import org.javaboy.tcrpc.exception.RpcException;

/**
 * @author:majin.wj
 */
public class NettyRpcServer implements RpcServer {

    private static final int processors = Runtime.getRuntime().availableProcessors();;

    private String ip;
    private Integer port;

    public NettyRpcServer(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void openServer() throws RpcException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup(processors);

        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(boss, worker)
                .channel(ServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {


                    }
                });
        ChannelFuture future = serverBootstrap.bind(this.ip, this.port).syncUninterruptibly();


    }
}
