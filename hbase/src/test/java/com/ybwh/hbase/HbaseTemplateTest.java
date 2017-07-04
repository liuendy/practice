package com.ybwh.hbase;

import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ybwh.hbase.spring.EntityMapper;

/**
 * TODO(这里用一句话描述这个类的作用)
 * 
 * @author Fanbeibei
 * @date 2017年5月25日 下午4:21:06
 * 
 *       1、win7下用hadoop-common-2.2.0-bin-master.zip中内容覆盖本地hadoop的bin目录
 *       2、在win7的hosts里配置域名
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-hbase.xml" })
public class HbaseTemplateTest {
	@Autowired
	private HbaseTemplate hbaseTemplate;

	@Test
	public void testOriginalFunction() {

		hbaseTemplate.find("merchant", "base", new RowMapper<Merchant>() {

			@Override
			public Merchant mapRow(Result result, int rowNum) throws Exception {

				System.out.println("获得到rowkey:" + new String(result.getRow()) + "  " + result.listCells().size());
				System.out.println();

				for (Cell cell : result.rawCells()) {
					System.out.println("列族：" + new String(CellUtil.cloneFamily(cell)));
					System.out.println("列：" + new String(CellUtil.cloneQualifier(cell)));

					System.out.println("====值:" + new String(CellUtil.cloneValue(cell)));
					System.out.println();

				}

				System.out.println("--------------------------------------");

				return null;
			}
		});

	}

	@Test
	public void testRowMaper() {
		try {
			List merchant = hbaseTemplate.find("merchant", "base", new EntityMapper(Merchant.class));
			System.out.println(merchant);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testPut() {
	}

}
