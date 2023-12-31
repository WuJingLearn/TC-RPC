package org.javaboy.tcrpc.server.remoting;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.javaboy.tcrpc.codec.RpcDecoder;
import org.javaboy.tcrpc.codec.RpcEncoder;
import org.javaboy.tcrpc.exception.RpcException;
import org.javaboy.tcrpc.server.process.handler.RpcServerHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author:majin.wj
 */
public class NettyRpcServer implements RpcServer {

    private static final int processors = Runtime.getRuntime().availableProcessors();
    private static final Logger LOG = LoggerFactory.getLogger(NettyRpcServer.class);

    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;

    private String ip;
    private Integer port;
    private InetSocketAddress address;


    public NettyRpcServer(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
        this.address = new InetSocketAddress(ip,port);
    }

    @Override
    public void openServer() throws RpcException {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup(processors);

        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("decoder", new RpcDecoder());
                        ch.pipeline().addLast("encoder", new RpcEncoder());
                        ch.pipeline().addLast("handler", new RpcServerHandler());
                    }
                });
        serverBootstrap.bind(this.ip, this.port).syncUninterruptibly();
        LOG.info("netty rpc server start success,address:{},port:{}", ip, port);
    }

    @Override
    public void shutDown() throws RpcException {
        try {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        } catch (Exception e) {
            throw new RpcException("server shutdown error", e);
        }
    }

    @Override
    public SocketAddress getAddress() {
        return address;
    }
}
