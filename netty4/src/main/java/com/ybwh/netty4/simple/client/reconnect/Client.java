package com.ybwh.netty4.simple.client.reconnect;

import io.netty.bootstrap.Bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * 自動重連的客戶端
 * 
 * @author fanbeibei
 *
 */
public class Client {

	private String host;
	private int port;

	private EventLoopGroup eventLoop = new NioEventLoopGroup();
	private Bootstrap bootstrap = new Bootstrap();
	private ReConnectHandler reConnectHandler;
	private ReconnectListener reconnectListener;

	public Client() {
		this("127.0.0.1", 17777);
	}

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		reConnectHandler = new ReConnectHandler(this);
		reconnectListener = new ReconnectListener(this);
	}

	public void start() {
		bootstrap.group(eventLoop);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {
				socketChannel.pipeline().addLast(reConnectHandler);

			}

		});

		bootstrap.remoteAddress(host, port);
		bootstrap.connect().addListener(reconnectListener);
	}

	public void reStart() {
		bootstrap = new Bootstrap();
		start();
	}

	public static void main(String[] args) {
		new Client().start();
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

}
