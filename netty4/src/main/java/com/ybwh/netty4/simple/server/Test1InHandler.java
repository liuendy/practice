package com.ybwh.netty4.simple.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Test1InHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

		System.out.println("channelRegistered  Test1InHandler");
//		Thread.sleep(20000);
		// ctx.fireChannelRegistered(); 不执行fire，不会传到下一个handler与servlet的filter相似
		super.channelRegistered(ctx);
		
		

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 客户端连上后会先channelRegistered再channelActive
		System.out.println("channelActive Test1InHandler");
		super.channelActive(ctx);
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 读事件来的时候先channelRead后channelReadComplete
		// super.channelRead(ctx, msg);
		System.out.println("channelRead");
		
		ByteBuf buf = (ByteBuf) msg;
		System.out.println(buf.readableBytes());

		ctx.fireChannelRead(msg);

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
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelUnregistered");

		super.channelUnregistered(ctx);

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		System.out.println("userEventTriggered");
		super.userEventTriggered(ctx, evt);

	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelWritabilityChanged");
		super.channelWritabilityChanged(ctx);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		cause.printStackTrace();
		ctx.close();
		System.out.println("exceptionCaught");
	}

}
