package com.nettm.exercise.netty.server.tcp;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TcpNioServerDemo {
	private static final int port = 7777;

	public static void main(String[] args) throws Exception {
		TcpNioServerDemo demo = new TcpNioServerDemo();
		demo.start();
	}
	
	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup(2);
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(group)
			.channel(NioServerSocketChannel.class)
			.childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
				@Override
				protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
					System.out.println("Receive data");
				}
			});
			 
			ChannelFuture f = b.bind(new InetSocketAddress(port));
			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						System.out.println("Server bound");
					} else {
						System.out.println("Bound attempt failed");
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
