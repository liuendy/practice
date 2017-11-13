package com.ybwh.utils;

public class TestBinary {
	public static void main(String[] args) {
		byte [] bytes = new byte []{(byte) 0b10000000};
		int j = (byte)(0b01000000);
		int i = bytes[0];
		System.out.println(i);

		
		byte b1 = (byte) 0b00000001;
		byte b2 = (byte) 0b11111111;
		System.out.println(b1);
		System.out.println(b2);
		

		
		
	}    

}
