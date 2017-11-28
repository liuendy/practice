package com.ybwh.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 传统的双亲委派加载器
 * 
 * @author fanbeibei
 *
 */
public class MyTraditionalClassLoader extends ClassLoader {
	private String rootDir;

	/*
	 * 重写这个方法可以覆写java.lang.ClassLoader中默认的加载委派规则
	 * 默认实现是先委托父加载器优先加载，如果父加载器加载不了则自己加载(调用findClass)
	 */
	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		return super.loadClass(name, resolve);//
	}

	public MyTraditionalClassLoader(String rootDir) {
		this.rootDir = rootDir;
	}

	/**
	 * 父加载器都加载不了才会走到这里
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = getClassData(name); // 获取类的字节数组
		if (classData == null) {
			throw new ClassNotFoundException();
		} else {
			/**
			 * defineClass才是真正的加载类，调用defineClass，才表示当前类是被加载类真正的加载器
			 */
			return defineClass(name, classData, 0, classData.length);
		}
	}

	private byte[] getClassData(String className) {
		// 读取类文件的字节
		String path = classNameToPath(className);
		try {
			InputStream ins = new FileInputStream(path);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int bytesNumRead = 0;
			// 读取类文件的字节码
			while ((bytesNumRead = ins.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesNumRead);
			}
			byte[] classBytes = baos.toByteArray();
			ins.close();
			baos.close();

			return classBytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String classNameToPath(String className) {
		// 得到类文件的完全路径
		return rootDir + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
	}
}
