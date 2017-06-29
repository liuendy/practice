package com.ybwh.netty4.simple.heartbeat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;

public class HeartBeat {
	private ScheduledExecutorService scheduledExcutor = Executors.newScheduledThreadPool(2);
	private Channel channel;
	/**
	 * 时间间隔(秒)
	 */
	private int period;

	public HeartBeat(int period, Channel channel) {
		this.period = period;
		this.channel = channel;
	}

	public void start() {
		scheduledExcutor.scheduleAtFixedRate(new HearBeatOp(), 0, period, TimeUnit.SECONDS);
	}

	private class HearBeatOp implements Runnable {

		@Override
		public void run() {
			channel.writeAndFlush("");
		}

	}

}
