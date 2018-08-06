package com.nettm.exercise.netty.server.socketio;

import java.net.InetSocketAddress;

import com.nettm.exercise.netty.handler.SocketIOChannelInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

public class SocketIOServer {

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private SocketIOChannelInitializer pipelineFactory = new SocketIOChannelInitializer();

    public static void main(String[] args) {
        SocketIOServer demo = new SocketIOServer();
        demo.start();
    }

    /**
     * Start server
     */
    public void start() {
        startAsync().syncUninterruptibly();
    }

    /**
     * Start server asynchronously
     * 
     * @return void
     */
    public Future<Void> startAsync() {
        initGroups();

        Class<? extends ServerChannel> channelClass = NioServerSocketChannel.class;
        if (isUseLinuxNativeEpoll()) {
            channelClass = EpollServerSocketChannel.class;
        }

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(channelClass).childHandler(pipelineFactory);
        applyConnectionOptions(b);

        InetSocketAddress addr = new InetSocketAddress(7777);

        return b.bind(addr).addListener(new FutureListener<Void>() {
            @Override
            public void operationComplete(Future<Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("SocketIO server started at port: 7777");
                } else {
                    System.out.println("SocketIO server start failed at port: 7777");
                }
            }
        });
    }

    /**
     * Stop server
     */
    public void stop() {
        bossGroup.shutdownGracefully().syncUninterruptibly();
        workerGroup.shutdownGracefully().syncUninterruptibly();
    }

    protected void initGroups() {
        if (isUseLinuxNativeEpoll()) {
            bossGroup = new EpollEventLoopGroup(1);
            workerGroup = new EpollEventLoopGroup(4);
        } else {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup(4);
        }
    }

    protected void applyConnectionOptions(ServerBootstrap bootstrap) {
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_SNDBUF, 65535);
        bootstrap.childOption(ChannelOption.SO_RCVBUF, 65535);
        bootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535));
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);
        bootstrap.childOption(ChannelOption.SO_LINGER, -1);

        bootstrap.option(ChannelOption.SO_REUSEADDR, false);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
    }

    private boolean isUseLinuxNativeEpoll() {
        return false;
    }

}
