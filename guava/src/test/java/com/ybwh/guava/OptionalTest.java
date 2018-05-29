package com.ybwh.guava;

import org.junit.Test;

import com.google.common.base.Optional;

/**
 * null会引起歧义，会造成让人迷惑的错误，有时也会让人感到不爽。Guava中的许多工具遇到null时，会拒绝或者马上报错，而不是盲目的接受。
 * 
 * 鉴于此google的guava库中提供了Optional接口来使null快速失败，即在可能为null的对象上做了一层封装，在使用Optional静态方法of时，
 * 如果传入的参数为null就抛出NullPointerException异常。
 * 
 * 在Guava中Optional类就是用来强制提醒程序员，注意对Null的判断。
 * 
 * java 8中也有Optional
 *
 */
public class OptionalTest {

	@Test
	public void test() {
		Integer i = 1234;
		Optional<Integer> oi = Optional.of(i);// 创建一个包含非空对象的Optional
		Optional<Integer> nullVal = Optional.absent();// 创建一个不包含对象的Optional
		Optional<Integer> nullAbleVal = Optional.fromNullable(i);// 从一个可空的对象返回的

		System.out.println(oi.isPresent());
		System.out.println(nullVal.isPresent());

		/**
		 * <pre>
		 * T get() 返回被包含的对象，如果实例是Absent，则抛出IllegalStateException 
		 * T or(T) 返回被包含的对象，如果实例是Absent，返回参数指定的对象
		 * T orNull() 返回被包含对象，如果实例时Absent，返回Null
		 * </pre>
		 */
		
		try {
			System.out.println(oi.get());
			System.out.println(nullVal.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(oi.or(4444));
			System.out.println(nullVal.or(4444));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(nullVal.orNull());
	}

}
