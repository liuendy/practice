package com.ybwh.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自己优先加载的加载器,破坏双亲加载机制一种方式， 不推荐使用，推荐用线程上下文加载器来破坏双亲加载机制
 * 
 * @author fanbeibei
 *
 */
public class MySelfDoClassLoader extends ClassLoader {
	private String rootDir;

	public MySelfDoClassLoader(String rootDir) {
		super(Thread.currentThread().getContextClassLoader());// 将线程上下文加载器设置为父加载器
		this.rootDir = rootDir;

	}

	/*
	 * 重写这个方法可以覆写java.lang.ClassLoader中默认的加载委派规则 改为一律由自己加载，不优先委托父加载器
	 */
	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

		if (name.startsWith("java.")) {// 如果是jdk的类则由父类加载器加载
			return getParent().loadClass(name);
		}

		Class<?> c = null;
		try {
			c = findClass(name);
			if (resolve) {
				resolveClass(c);
			}
		} catch (Throwable e) {
			c = getParent().loadClass(name);// 自己加载出错则由父类加载器加载
		}

		return c;
	}

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
