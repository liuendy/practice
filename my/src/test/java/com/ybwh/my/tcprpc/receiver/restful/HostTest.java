package com.ybwh.my.tcprpc.receiver.restful;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class HostTest {
	public static void main(String[] args) {
		Socket socket = new Socket();
		try {
			// 5. 通过Socket的方式获取Host
			// 一般解析到这里, 都会获取到正确的本地IP, 除非你有多网卡, 或者有VPN,
			// 导致无法正常解析.
			SocketAddress addr = new InetSocketAddress("10.0.11.22",
					2181);
			socket.connect(addr, 1000);
			String host = socket.getLocalAddress().getHostAddress();
			System.out.println(host);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Throwable e) {
			}
		}
	}

}
