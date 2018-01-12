package com.ybwh.concurrent.unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeUtil {
	/**
	 * 获取 Unsafe 对象
	 * @return
	 */
	public static Unsafe getUnsafe() {  
		/*
		 * 通过反射绕过安全检查
		 */
        try {  
            Field field = Unsafe.class.getDeclaredField("theUnsafe");  
            field.setAccessible(true);  
            return (Unsafe)field.get(null);  
              
        } catch (Exception e) {  
        }  
        return null;  
    } 
}
