package com.ybwh.netty4.simple.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Test1InHandler extends ChannelInboundHandlerAdapter {



	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 客户端连上后会先channelRegistered再channelActive
		System.out.println("channelActive Test1InHandler");
//		super.channelActive(ctx);
		
//		ctx.channel().close();
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 读事件来的时候先channelRead后channelReadComplete
		// super.channelRead(ctx, msg);
		System.out.println("channelRead");
		
		ByteBuf buf = (ByteBuf) msg;
		System.out.println(buf.readableBytes());

//		ctx.fireChannelRead(msg);
		
		
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelReadComplete");

		super.channelReadComplete(ctx);

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelInactive");

		super.channelInactive(ctx);

	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		cause.printStackTrace();
		ctx.close();
		System.out.println("exceptionCaught");
	}

}
