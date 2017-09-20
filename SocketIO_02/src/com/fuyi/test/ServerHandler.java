package com.fuyi.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// do something msg
			//读取客户端数据
			ByteBuf buf = (ByteBuf)msg;
			byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			String req = new String(data, "utf-8");
			System.out.println("server:" + req);
			
			//写给客户端
			//ctx.write(Unpooled.copiedBuffer("888".getBytes()));
			//ctx.flush();
			
			ctx.writeAndFlush(Unpooled.copiedBuffer("888".getBytes()))
			.addListener(ChannelFutureListener.CLOSE);
			
			
			
			/*while(in.isReadable()) {
				System.out.println((char)in.readByte());
				System.out.flush();
			}*/
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
