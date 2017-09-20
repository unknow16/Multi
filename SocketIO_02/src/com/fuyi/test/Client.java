package com.fuyi.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	public static void main(String[] args) throws Exception {
		
		EventLoopGroup workGroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workGroup)
		.channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				sc.pipeline().addLast(new ClientHandler());
			}
			
		});
		
		ChannelFuture cf1 = b.connect("127.0.0.1", 4321).sync();
		cf1.channel().write(Unpooled.copiedBuffer("777".getBytes()));
		cf1.channel().flush();
		
		cf1.channel().closeFuture().sync();
		workGroup.shutdownGracefully();
	}
}
