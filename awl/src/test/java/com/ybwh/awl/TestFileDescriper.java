package com.ybwh.awl;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestFileDescriper {

	public static void main(String[] args) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("E:/github/practice/awl/FD.txt",true);
			FileDescriptor fileDescriptor = fileOutputStream.getFD();

			System.out.printf("fileDescriptor.valid() = %s \n" , fileDescriptor.valid());

			FileOutputStream fileOutputStream1 = new FileOutputStream(fileDescriptor);
			fileOutputStream1.write('a');

			fileOutputStream.write('A');

			fileOutputStream.close();
			fileOutputStream1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
