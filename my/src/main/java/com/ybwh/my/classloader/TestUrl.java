package com.ybwh.my.classloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;

public class TestUrl {
	@Test
	public void urlAndFile() {
		// url 与file转换
		try {
			// URL转File
			URL u1 = new URL("file:D:/work/hadoop.txt");
			String fpath1 = u1.getFile();
			System.out.println(fpath1);
			File f2 = new File(fpath1);

			// File转URL
			URL u2 = f2.toURI().toURL();
			System.out.println((u1.equals(u2)) + "," + u2);

			// url 转stream
			InputStream is = u1.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			int len = 0;
			char[] bytes = new char[10];
			while (-1 != (len = reader.read(bytes))) {
				System.out.print(bytes);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testJarUrl() {
		try {
			URL url = new URL(
					"jar:file:/D:/appInstall/gradle-3.5/lib/ant-1.9.6.jar!/org/apache/tools/zip/ZipUtil.class");
			
			
			// 读取jar文件
			JarURLConnection conn = (JarURLConnection) url.openConnection();
			JarFile jarfile = conn.getJarFile();
			System.out.println(jarfile);
			 Enumeration<JarEntry> enu = jarfile.entries();
			while (enu.hasMoreElements()) {
				JarEntry element = (JarEntry) enu.nextElement();
				String name = element.getName();
				Long size = element.getSize();
				Long time = element.getTime();
				Long compressedSize = element.getCompressedSize();

				System.out.print(name + "/t");
				System.out.print(size + "/t");
				System.out.print(compressedSize + "/t");
				System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date(time)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testFileJar(){
		
		try {
			File jf  = new File("D:/appInstall/gradle-3.5/lib/ant-1.9.6.jar");
			JarFile jarFile = new JarFile(jf);
			
			System.out.println(jarFile);
			 Enumeration<JarEntry> enu = jarFile.entries();
			while (enu.hasMoreElements()) {
				JarEntry element = (JarEntry) enu.nextElement();
				String name = element.getName();
				Long size = element.getSize();
				Long time = element.getTime();
				Long compressedSize = element.getCompressedSize();

				System.out.print(name + "/t");
				System.out.print(size + "/t");
				System.out.print(compressedSize + "/t");
				System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date(time)));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testReadMetaInfo(){
		
		try {
			Enumeration<URL> urls = this.getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
//			urls = ClassLoader.getSystemResources("META-INF/");
//			System.out.println(urls.hasMoreElements());
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				System.out.println("url="+url);
				// url 转stream
				InputStream is = url.openStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				int len = 0;
				String line = null;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
				
				
			}
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
