package com.fuyi.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			/*ByteBuf in = (ByteBuf) msg;
			while (in.isReadable()) {
				System.out.println((char) in.readByte());
				System.out.flush();
			}*/
			
			ByteBuf buf = (ByteBuf)msg;
			byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			String req = new String(data, "utf-8");
			System.out.println("server:" + req);
			
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

}
