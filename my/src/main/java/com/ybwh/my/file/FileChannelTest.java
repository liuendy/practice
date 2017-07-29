package com.ybwh.my.file;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.junit.Test;

/**
 * 并发读写文件
 * @author fanbeibei
 *
 */
public class FileChannelTest {
	
	/**
     * 用filechannel进行文件复制(零拷贝方式)
     *
     * @param fromFile 源文件
     * @param toFile   目标文件
     */
    public void fileCopyWithFileChannel(File fromFile, File toFile) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileChannelInput = null;
        FileChannel fileChannelOutput = null;
        try {
            fileInputStream = new FileInputStream(fromFile);
            fileOutputStream = new FileOutputStream(toFile);
            //得到fileInputStream的文件通道
            fileChannelInput = fileInputStream.getChannel();
            //得到fileOutputStream的文件通道
            fileChannelOutput = fileOutputStream.getChannel();
            //将fileChannelInput通道的数据，写入到fileChannelOutput通道
            fileChannelInput.transferTo(0, fileChannelInput.size(), fileChannelOutput);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
                if (fileChannelInput != null)
                    fileChannelInput.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
                if (fileChannelOutput != null)
                    fileChannelOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	
	
	@Test
	public void testFileLock(){
		
		Runnable fileTask = new Runnable() {
			
			@Override
			public void run() {
				try {
					RandomAccessFile targetFile = new RandomAccessFile("D:/myFile/lock", "rw");
					FileLock lock = targetFile.getChannel().tryLock();
					if(null == lock){
						System.out.println("locked");
					}else{
						
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		};
		
		
		
		
		
	}
	
	
}
