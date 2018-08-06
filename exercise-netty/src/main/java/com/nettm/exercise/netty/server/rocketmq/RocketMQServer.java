package com.nettm.exercise.netty.server.rocketmq;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.nettm.exercise.netty.handler.HandshakeHandler;
import com.nettm.exercise.netty.handler.NettyConnectManageHandler;
import com.nettm.exercise.netty.handler.NettyDecoder;
import com.nettm.exercise.netty.handler.NettyEncoder;
import com.nettm.exercise.netty.handler.NettyServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

public class RocketMQServer {

    private static final String HANDSHAKE_HANDLER_NAME = "handshakeHandler";

    private ServerBootstrap serverBootstrap;

    private DefaultEventExecutorGroup defaultEventExecutorGroup;

    private EventLoopGroup eventLoopGroupBoss;

    private EventLoopGroup eventLoopGroupSelector;

    public static void main(String[] args) {
        RocketMQServer demo = new RocketMQServer();
        demo.start();
    }

    public void start() {
        serverBootstrap = new ServerBootstrap();

        defaultEventExecutorGroup = new DefaultEventExecutorGroup(8, new ThreadFactory() {

            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "NettyServerCodecThread_" + this.threadIndex.incrementAndGet());
            }
        });

        eventLoopGroupBoss = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyBoss_%d", this.threadIndex.incrementAndGet()));
            }
        });

        if (useEpoll()) {
            eventLoopGroupSelector = new EpollEventLoopGroup(3, new ThreadFactory() {
                private AtomicInteger threadIndex = new AtomicInteger(0);
                private int threadTotal = 3;

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, String.format("NettyServerEPOLLSelector_%d_%d", threadTotal,
                            this.threadIndex.incrementAndGet()));
                }
            });
        } else {
            eventLoopGroupSelector = new NioEventLoopGroup(3, new ThreadFactory() {
                private AtomicInteger threadIndex = new AtomicInteger(0);
                private int threadTotal = 3;

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, String.format("NettyServerNIOSelector_%d_%d", threadTotal,
                            this.threadIndex.incrementAndGet()));
                }
            });
        }

        ServerBootstrap childHandler = serverBootstrap.group(eventLoopGroupBoss, eventLoopGroupSelector)
                .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_SNDBUF, 65535)
                .childOption(ChannelOption.SO_RCVBUF, 65535).localAddress(new InetSocketAddress(7777))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(defaultEventExecutorGroup, HANDSHAKE_HANDLER_NAME, new HandshakeHandler())
                                .addLast(defaultEventExecutorGroup, new NettyEncoder(), new NettyDecoder(),
                                        new IdleStateHandler(0, 0, 120), new NettyConnectManageHandler(),
                                        new NettyServerHandler());
                    }
                });

        childHandler.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        try {
            ChannelFuture sync = serverBootstrap.bind().sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e1) {
            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e1);
        }

    }

    public void shutdown() {
        try {
            eventLoopGroupBoss.shutdownGracefully();

            eventLoopGroupSelector.shutdownGracefully();

            if (defaultEventExecutorGroup != null) {
                defaultEventExecutorGroup.shutdownGracefully();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean useEpoll() {
        return false;
    }

}
