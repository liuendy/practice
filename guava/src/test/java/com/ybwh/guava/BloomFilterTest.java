package com.ybwh.guava;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.PrimitiveSink;

/**
 * 布隆过滤
 *
 */
public class BloomFilterTest {
	@Test
	public void testBase() {
		int size = 10000;
		BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size);
		for (int i = 0; i < size; i++) {
			bloomFilter.put(i);
		}

		for (int i = 0; i < size; i++) {
			if (!bloomFilter.mightContain(i)) {
				System.out.println("有坏人逃脱了");
			}
		}

		List<Integer> list = new ArrayList<Integer>(1000);
		for (int i = size + 10000; i < size + 20000; i++) {
			if (bloomFilter.mightContain(i)) {
				list.add(i);
			}
		}
		System.out.println("有误伤的数量：" + list.size());
	}

	@Test
	public void test() {
		int size = 10000;
		BloomFilter<Integer> bloomFilter = BloomFilter.create(new Funnel<Integer>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void funnel(Integer arg0, PrimitiveSink arg1) {

				arg1.putInt(arg0);
			}

		}, 1024 * 1024 * 32, 0.0000001d);
		for (int i = 0; i < size; i++) {
			bloomFilter.put(i);
		}

		for (int i = 0; i < size; i++) {
			if (!bloomFilter.mightContain(i)) {
				System.out.println("有坏人逃脱了");
			}
		}

		List<Integer> list = new ArrayList<Integer>(1000);
		for (int i = size + 10000; i < size + 20000; i++) {
			if (bloomFilter.mightContain(i)) {
				list.add(i);
			}
		}
		System.out.println("有误伤的数量：" + list.size());
	}

}
