package com.ybwh.concurrent;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class TestUnSafe {

	public static void main(String[] args) {
		/*AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
            	Unsafe unsafe = Unsafe.getUnsafe();
        		System.out.println(unsafe);
        		return unsafe;
            }
        });*/
		Unsafe unsafe = getUnsafe();
		System.out.println(unsafe);
		
	}
	
	public static Unsafe getUnsafe() {  
        try {  
            Field field = Unsafe.class.getDeclaredField("theUnsafe");  
            field.setAccessible(true);  
            return (Unsafe)field.get(null);  
              
        } catch (Exception e) {  
        }  
        return null;  
    }  
}
