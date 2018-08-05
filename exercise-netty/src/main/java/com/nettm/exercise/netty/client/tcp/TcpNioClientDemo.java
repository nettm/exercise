package com.nettm.exercise.netty.client.tcp;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TcpNioClientDemo {
	
	private static final String host = "127.0.0.1";
	
	private static final int port = 7777;

	public static void main(String[] args) throws Exception {
		TcpNioClientDemo demo = new TcpNioClientDemo();
		demo.start();
	}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.handler(new SimpleChannelInboundHandler<ByteBuf>() {
				@Override
				protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
					System.out.println("Received data");
				}
			});
			ChannelFuture f = b.connect(new InetSocketAddress(host,port));
			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						System.out.println("Connection established");
					} else {
						System.out.println("Connection attempt failed");
						future.cause().printStackTrace();
					}
				}
			});
			f.sync();
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

	public void shutdown() {

	}

	public String read() {
		return null;
	}

	public void write(String message) {

	}

}
