package com.fbb.jersey.server;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.Test;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.container.grizzly2.GrizzlyWebContainerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class Server {

	@Test
	public void testGrizzlyServerFactory() {
		URI uri = UriBuilder.fromUri("http://127.0.0.1").port(9000).build();
		ResourceConfig rc = new PackagesResourceConfig("com.fbb.jersey.server.service");
		// rc.getFeature(featureName)
		try {
			HttpServer server = GrizzlyServerFactory.createHttpServer(uri, rc);
			server.start();
			System.out.println("server start!!");
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test 
	public void testGrizzlyWebContainerFactory() {
		final String baseUri = "http://127.0.0.1:9000/";
		final HashMap<String, String> initParams = new HashMap<String, String>();

		initParams.put("jersey.config.server.provider.packages", "com.fbb.jersey.server.service");// packages定义了Jersey寻找服务类的位置。
//		initParams.put("mypram", "33333333333333");																		// 它必须指向定义的资源类。

		HttpServer server = null;
		try {
			server = GrizzlyWebContainerFactory.create(baseUri, initParams);
			server.start();
			System.out.println("...started");

			System.in.read();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != server) {
				server.stop();
			}

		}

	}
}
