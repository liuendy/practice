package com.ybwh.spi;

import java.util.ServiceLoader;

import com.ybwh.spi.Speaker;

public class SpiMain {

	public static void main(String[] args) {
		ServiceLoader<Speaker> loaders = 
	              ServiceLoader.load(Speaker.class);
		
		for(Speaker s:loaders){
			s.say();
		}

	}

}
