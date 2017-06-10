package com.ybwh.netty4.simple.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleClient {
	public static void main(String[] args) {
		String host = "";
		int port = 17777;

		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					
					// ch.pipeline().addLast(new TimeClientHandler());
					
//					ByteToMessageDecoder
				}
			});
			// 启动客户端
			ChannelFuture f = b.connect(host, port).sync(); // (5)
			
			Channel channel = f.channel();
			channel.writeAndFlush("fdsahfsaklgigasd9999999999999");
			
			// 等待连接关闭
			f.channel().closeFuture().sync();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
