package com.nettm.exercise.netty.client.dubbo;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import com.nettm.exercise.netty.handler.NettyDecoder;
import com.nettm.exercise.netty.handler.NettyEncoder;
import com.nettm.exercise.netty.handler.NettyServerHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

public class DubboClient {

    private static final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(5, new DefaultThreadFactory("NettyClientWorker", true));

    private Bootstrap bootstrap;

    private volatile Channel channel; // volatile, please copy reference to use

    public static void main(String[] args) throws Throwable {
        DubboClient demo = new DubboClient();
        demo.doOpen();
        demo.doConnect();
    }

    protected void doOpen() throws Throwable {
        bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                //.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getTimeout())
                .channel(NioSocketChannel.class);

        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {

            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline()
                    .addLast("decoder", new NettyDecoder())
                    .addLast("encoder", new NettyEncoder())
                    .addLast("handler", new NettyServerHandler());
            }

        });
    }

    protected void doConnect() throws Throwable {
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 7777));
        boolean ret = future.awaitUninterruptibly(3000, TimeUnit.MILLISECONDS);

        if (ret && future.isSuccess()) {
            channel = future.channel();
            channel.closeFuture().sync();
        } else {
            System.out.println("Connect failed.");
        }
        
    }

}
