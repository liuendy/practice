package com.ybwh.fileop.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class StreamWriteFileTask implements Runnable{
	private String text;
	private FileOutputStream fos;
	private int sleep;
	
	public  StreamWriteFileTask(String text,File file,int sleep) throws FileNotFoundException {
		this.text = text;
		fos = new FileOutputStream(file);
		this.sleep = sleep;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			for (int i = 0; i < 10; i++) {
					fos.write(text.getBytes("UTF-8"));
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				
				fos.close();
				System.out.println(text+" "+System.currentTimeMillis());
			} catch (IOException e) {
			}
		}
		
		
		
	}

}
