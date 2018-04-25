package com.ybwh.springtask;

public class TestFinnally {
	public static void main(String[] args) {
		System.out.println("***********"+test());
	}
	
	public static int test() {
		for (int i = 0; i < 10; i++) {
			try {
				System.out.println("try "+i);
//				continue;
//				break;
				return i;
			}finally {
				System.out.println("finally "+i);
				return 3333;
			}
		}
		return 0;
	}

}
