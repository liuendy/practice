package com.ybwh.netty4.simple.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatInHandler extends ChannelInboundHandlerAdapter {
	private int loss_connect_time;
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt; 
			
			if (event.state() == IdleState.READER_IDLE) {  
                loss_connect_time++;  
                System.out.println("5 秒没有接收到客户端的信息了");  
                if (loss_connect_time > 2) {  
                    System.out.println("关闭这个不活跃的channel");  
                    ctx.channel().close();  
                }  
            } 
			
		}else {
			ctx.fireUserEventTriggered(evt);
		}
	}
}
