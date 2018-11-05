package com.ybwh.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JettyServer {
	private Server server;
	private Thread thread;
	
	public void start(final int port, final String ip, final String appName) throws Exception {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {

				// The Server
				server = new Server(new ExecutorThreadPool());  // 非阻塞

				// HTTP connector
				ServerConnector connector = new ServerConnector(server);
				if (ip!=null && ip.trim().length()>0) {
					//connector.setHost(ip);	// The network interface this connector binds to as an IP address or a hostname.  If null or 0.0.0.0, then bind to all interfaces.
				}
				connector.setPort(port);
				server.setConnectors(new Connector[]{connector});

				// Set a handler
				HandlerCollection handlerc =new HandlerCollection();
				handlerc.setHandlers(new Handler[]{new JettyServerHandler()});
				server.setHandler(handlerc);

				try {
					// Start server
					server.start();
					log.info(">>>>>>>>>>>  jetty server start success at port:{}.", port);

					

					server.join();	// block until thread stopped
					log.info(">>>>>>>>>>>  server join success, netcon={}, port={}", JettyServer.class.getName(), port);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				} finally {
					//destroy();
				}
			}
		});
		thread.setDaemon(true);	// daemon, service jvm, user thread leave >>> daemon leave >>> jvm leave
		thread.start();
	}

	public void destroy() {
		// destroy server
		if (server != null) {
			try {
				server.stop();
				server.destroy();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		if (thread.isAlive()) {
			thread.interrupt();
		}

		log.info(">>>>>>>>>>> xxl-rpc server destroy success, netcon={}", JettyServer.class.getName());
	}
	
	public static void main(String[] args) {
		
//		new JettyServer().start(port, ip, appName);
		
	}
}
