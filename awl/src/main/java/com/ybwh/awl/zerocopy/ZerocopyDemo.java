package com.ybwh.awl.zerocopy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class ZerocopyDemo {
	@SuppressWarnings("resource")
	public static void transferToDemo(String from, String to) throws IOException {
		FileChannel fromChannel = new RandomAccessFile(from, "rw").getChannel();
		FileChannel toChannel = new RandomAccessFile(to, "rw").getChannel();

		long position = 0;
		long count = fromChannel.size();

		fromChannel.transferTo(position, count, toChannel);

		fromChannel.close();
		toChannel.close();
	}

	@SuppressWarnings("resource")
	public static void transferFromDemo(String from, String to) throws IOException {
		FileChannel fromChannel = new FileInputStream(from).getChannel();
		FileChannel toChannel = new FileOutputStream(to).getChannel();

		long position = 0;
		long count = fromChannel.size();

		toChannel.transferFrom(fromChannel, position, count);

		fromChannel.close();
		toChannel.close();
	}

	public static void main(String[] args) throws IOException {
		String from = "src/main/java/zerocopy/1.data";
		String to = "src/main/java/zerocopy/2.data";
		// transferToDemo(from,to);
		transferFromDemo(from, to);
	}
}