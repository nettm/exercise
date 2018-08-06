package com.nettm.exercise.netty.client.rocketmq;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.nettm.exercise.netty.handler.NettyClientHandler;
import com.nettm.exercise.netty.handler.NettyConnectManageHandler;
import com.nettm.exercise.netty.handler.NettyDecoder;
import com.nettm.exercise.netty.handler.NettyEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

public class RocketMQClient {

    private Bootstrap bootstrap = new Bootstrap();

    private EventLoopGroup eventLoopGroupWorker;

    private DefaultEventExecutorGroup defaultEventExecutorGroup;

    public static void main(String[] args) {
        RocketMQClient demo = new RocketMQClient();
        demo.start();
    }

    public void start() {
        defaultEventExecutorGroup = new DefaultEventExecutorGroup(4, new ThreadFactory() {

            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "NettyClientWorkerThread_" + threadIndex.incrementAndGet());
            }
        });

        eventLoopGroupWorker = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyClientSelector_%d", threadIndex.incrementAndGet()));
            }
        });

        Bootstrap handler = bootstrap.group(eventLoopGroupWorker).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000).option(ChannelOption.SO_SNDBUF, 65535)
                .option(ChannelOption.SO_RCVBUF, 65535).handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(defaultEventExecutorGroup, new NettyEncoder(), new NettyDecoder(),
                                new IdleStateHandler(0, 0, 120), new NettyConnectManageHandler(),
                                new NettyClientHandler());
                    }
                });
        try {
            ChannelFuture f = handler.connect(new InetSocketAddress("127.0.0.1", 7777)).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            eventLoopGroupWorker.shutdownGracefully();

            if (defaultEventExecutorGroup != null) {
                defaultEventExecutorGroup.shutdownGracefully();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
