package com.ybwh.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Query;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.RowMutations;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.ColumnPaginationFilter;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.DependentColumnFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.junit.Test;

import com.thoughtworks.xstream.mapper.Mapper.Null;

import sun.tools.tree.BinaryArithmeticExpression;

/** 
* TODO(这里用一句话描述这个类的作用) 
* @author Fanbeibei
* @date 2017年5月11日 下午6:11:00 
*  
*/
/**
 * TODO(这里用一句话描述这个类的作用)
 * 
 * @author Fanbeibei
 * @date 2017年5月11日 下午6:11:03
 * 
 * 
 * 1、win7下用hadoop-common-2.2.0-bin-master.zip中内容覆盖本地hadoop的bin目录
 * 2、在win7的hosts里配置域名
 * 
 * 
 */
public class HbaseTest {

	public static Configuration configuration;
	static {
		configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		configuration.set("hbase.zookeeper.quorum", "10.0.11.22,10.0.11.23,10.0.11.24");
		configuration.set("hbase.master", "10.0.11.21:60000,10.0.11.25:60000");
	}

	public static void main(String[] args) {
		System.setProperty("hadoop.home.dir", "D:/soft/java/hadoop-2.7.3");

		// compareAndSet("merchant");
//		 insertData("merchant");
		// queryByRowKey("merchant", "rr222");
		// updateData("merchant");

		queryAll("merchant");
		
		queryByRowkeyRange("merchant","","");

	}

	public static void queryAll(String tableName) {
		Connection connection = null;
		Table table = null;
		ResultScanner rs = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));
			// 返回的实际是HTable
			// System.out.println(table.getClass());
			// HTable ht = (HTable) table;

			// Use the table as needed, for a single operation and a single
			// thread

			Scan scan = new Scan();
			// scan.addColumn(family, qualifier)
			// scan.addFamily(family)

			// scan.setStartRow(startRow);
			// scan.setStopRow(stopRow);

			// rowkey过滤
//			scan.setFilter(
//					new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator("rowkey".getBytes())));
//
//			// 列值过滤
//			scan.setFilter(new DependentColumnFilter("family".getBytes(), "qualifier".getBytes(), false,
//					CompareFilter.CompareOp.EQUAL, new BinaryComparator("".getBytes())));

			// 性能相关
			scan.setCaching(10);// 设置客户端一次rpc批量执行10个next()，并将结果缓存在客户；不设则rs一次rpc只执行一个next()
			scan.setBatch(1);// 设置客户端一次next()返回的Result包含5列,一般设为可以被列数整除的值，不设则是返回全部列

			rs = table.getScanner(scan);

			for (Result r : rs) {
				System.out.println("获得到rowkey:" + new String(r.getRow()) + "  " + r.listCells().size());
				System.out.println();

				for (Cell cell : r.rawCells()) {
					System.out.println("列族：" + new String(CellUtil.cloneFamily(cell)));
					System.out.println("列：" + new String(CellUtil.cloneQualifier(cell)));

					System.out.println("====值:" + new String(CellUtil.cloneValue(cell)));
					System.out.println();

				}

				System.out.println("------------------------------------------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 分页
	 * 
	 * @param tableName
	 */
	public static void queryPagination(String tableName) {
		Connection connection = null;
		Table table = null;
		ResultScanner rs = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));
			// 返回的实际是HTable
			// System.out.println(table.getClass());
			// HTable ht = (HTable) table;

			// Use the table as needed, for a single operation and a single
			// thread

			Scan scan = new Scan();

			Filter filter = new ColumnPaginationFilter(5, 15);
			scan.setFilter(filter);

			// 性能相关
			scan.setCaching(10);// 设置客户端一次rpc批量执行10个next()，并将结果缓存在客户；不设则rs一次rpc只执行一个next()
			scan.setBatch(1);// 设置客户端一次next()返回的Result包含5列,一般设为可以被列数整除的值，不设则是返回全部列

			rs = table.getScanner(scan);

			for (Result r : rs) {
				System.out.println("获得到rowkey:" + new String(r.getRow()) + "  " + r.listCells().size());
				System.out.println();

				for (Cell cell : r.rawCells()) {
					System.out.println("列族：" + new String(CellUtil.cloneFamily(cell)));
					System.out.println("列：" + new String(CellUtil.cloneQualifier(cell)));

					System.out.println("====值:" + new String(CellUtil.cloneValue(cell)));
					System.out.println();

				}

				System.out.println("------------------------------------------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 单条件查询,根据rowkey查询唯一一条记录
	 * 
	 * @param tableName
	 */
	public static void queryByRowKey(String tableName, String rowKey) {

		Connection connection = null;
		Table table = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));
			// 返回的实际是HTable
			// System.out.println(table.getClass());
			// HTable ht = (HTable) table;

			// Use the table as needed, for a single operation and a single
			// thread

			Get get = new Get(rowKey.getBytes());

			// 指定返回列
			get.addColumn("base".getBytes("UTF-8"), "name".getBytes());

			// 指定返回列族
			// get.addFamily("base".getBytes("UTF-8"));

			Result r = table.get(get);

			System.out.println("获得到rowkey:" + new String(r.getRow()) + "  " + r.listCells().size());
			System.out.println();

			for (Cell cell : r.rawCells()) {
				System.out.println("列族：" + new String(CellUtil.cloneFamily(cell)));
				System.out.println("列：" + new String(CellUtil.cloneQualifier(cell)));

				System.out.println("====值:" + new String(CellUtil.cloneValue(cell)));
				System.out.println();

				// System.out.println("列：" + new String(cell.getFamilyArray()));
				// System.out.println( "====值:" + new
				// String(cell.getValueArray()));

			}
			

			/*
			 * for (KeyValue keyValue : r.raw()) { System.out.println("列：" + new
			 * String(keyValue.getFamily()) // use CellUtil.getFamilyArray
			 * CellUtil.cloneFamily(this); + "====值:" + new
			 * String(keyValue.getValue())); // use CellUtil.getValueArray()
			 * CellUtil.cloneValue(this); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 根据rowkey作范围查询
	 * 
	 * @param tableName
	 * @param startRowkey
	 * @param endRowkey
	 */
	public static void queryByRowkeyRange(String tableName,String startRowkey,String endRowkey){
		Connection connection = null;
		Table table = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));
			// 返回的实际是HTable
			// System.out.println(table.getClass());
			// HTable ht = (HTable) table;

			// Use the table as needed, for a single operation and a single
			// thread

			Scan scan = new Scan(startRowkey.getBytes("UTF-8"), endRowkey.getBytes("UTF-8"));
			// 指定返回列
			scan.addColumn("base".getBytes("UTF-8"), "name".getBytes());

			// 指定返回列族
			// scan.addFamily("base".getBytes("UTF-8"));
			
			//offset  limit 
			scan.setRowOffsetPerColumnFamily(0);
			scan.setMaxResultSize(10);
			
			//设置每次next()返回的行数，性能相关
//			scan.setBatch(10);
			
			
			//对查询结果筛选
//			scan.setFilter(filter)

			ResultScanner rs = table.getScanner(scan);
			
			for(Result r = rs.next();null != r;r = rs.next()){
				System.out.println("获得到rowkey:" + new String(r.getRow()) + "  " + r.listCells().size());
				System.out.println();

				for (Cell cell : r.rawCells()) {
					System.out.println("列族：" + new String(CellUtil.cloneFamily(cell))+
							"列：" + new String(CellUtil.cloneQualifier(cell))+
							"====值:" + new String(CellUtil.cloneValue(cell)));
					System.out.println();

					// System.out.println("列：" + new String(cell.getFamilyArray()));
					// System.out.println( "====值:" + new
					// String(cell.getValueArray()));

				}
			}
			
			
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 插入数据
	 * 
	 * @param tableName
	 */
	public static void insertData(String tableName) {
		System.out.println("start insert data ......");

		Connection connection = null;
		Table table = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));

			// 返回的实际是HTable
			// System.out.println(table.getClass());
			// HTable ht = (HTable) table;

			// Use the table as needed, for a single operation and a single
			// thread

			Put put = new Put("rr222".getBytes());// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值

			put.addColumn("base".getBytes("UTF-8"), "name".getBytes("UTF-8"), "kkk".getBytes());
			put.addColumn("base".getBytes(), "mobile".getBytes("UTF-8"), "13245896574".getBytes());
			put.addColumn("base".getBytes(), "money".getBytes("UTF-8"), "78.23".getBytes());

			// put.addColumn(Bytes.toBytes("base"), Bytes.toBytes("name"),
			// Bytes.toBytes("kkk"));
			// put.addColumn(Bytes.toBytes("base"), Bytes.toBytes("mobile"),
			// Bytes.toBytes("13245896574"));
			// put.addColumn(Bytes.toBytes("base"), Bytes.toBytes("money"),
			// Bytes.toBytes("78.23"));

			table.put(put);

			// 批量时
			/*
			 * HTable ht = (HTable) table; ht.setAutoFlush(false, true);
			 * ht.put(put); // ht.put(put2); // ht.put(put3); ht.flushCommits();
			 */

			System.out.println("put");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("end insert data ......");
	}

	public static void deleteData(String tableName) {
		System.out.println("start delete data ......");

		Connection connection = null;
		Table table = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));

			// 返回的实际是HTable
			// System.out.println(table.getClass());
			// HTable ht = (HTable) table;

			// Use the table as needed, for a single operation and a single
			// thread

			// 根据rowkey删除
			Delete delete = new Delete("rowkey".getBytes());
			// 指定要删除的列,不指定删除整行
			// delete.addColumn(family, qualifier)
			// delete.addFamily(family)

			table.delete(delete);
			// table.

			// 批量时
			/*
			 * HTable ht = (HTable) table; ht.setAutoFlush(false, true);
			 * ht.put(put); // ht.put(put2); // ht.put(put3); ht.flushCommits();
			 */

			System.out.println("delete");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("end delete data ......");
	}

	/**
	 * 更新数据，和插入一样
	 * 
	 * @param tableName
	 */
	public static void updateData(String tableName) {
		System.out.println("start update data ......");

		Connection connection = null;
		Table table = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));

			// 返回的实际是HTable
			// System.out.println(table.getClass());
			// HTable ht = (HTable) table;

			// Use the table as needed, for a single operation and a single
			// thread

			Put put = new Put("rr222".getBytes());// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值

			// 更新哪列加哪列
			put.addColumn("base".getBytes("UTF-8"), "name".getBytes("UTF-8"), "tttttt".getBytes());
			// put.addColumn("base".getBytes(), "mobile".getBytes("UTF-8"),
			// "13245896574".getBytes());
			// put.addColumn("base".getBytes(), "money".getBytes("UTF-8"),
			// "78.23".getBytes());

			// rowkey存在则更新，不存在则插入
			table.put(put);

			// table.checkAndPut(row, family, qualifier, value, put);

			// 批量时
			/*
			 * HTable ht = (HTable) table; ht.setAutoFlush(false, true);
			 * ht.put(put); // ht.put(put2); // ht.put(put3); ht.flushCommits();
			 */

			System.out.println("put");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("end update data ......");
	}

	/**
	 * 原子操作-比较更新
	 * 
	 * @param tableName
	 */
	public static void compareAndSet(String tableName) {

		System.out.println("start compareAndSet data ......");

		Connection connection = null;
		Table table = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));

			// 返回的实际是HTable
			// System.out.println(table.getClass());
			// HTable ht = (HTable) table;

			// Use the table as needed, for a single operation and a single
			// thread

			Put put = new Put("rr222".getBytes());// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值

			put.addColumn("base".getBytes("UTF-8"), "name".getBytes("UTF-8"), "rrrr".getBytes());
			put.addColumn("base".getBytes(), "mobile".getBytes("UTF-8"), "13245896574".getBytes());
			put.addColumn("base".getBytes(), "money".getBytes("UTF-8"), "78.23".getBytes());

			table.checkAndPut("rr222".getBytes(), "base".getBytes("UTF-8"), "name".getBytes("UTF-8"),
					"tttttt".getBytes("UTF-8"), put);

			// 批量时
			/*
			 * HTable ht = (HTable) table; ht.setAutoFlush(false, true);
			 * ht.put(put); // ht.put(put2); // ht.put(put3); ht.flushCommits();
			 */

			System.out.println("put");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("end compareAndSet data ......");

	}

	/**
	 * 原子操作-列值自增
	 * 
	 * @param tableName
	 */
	public static void incrementColumnValue(String tableName) {

		System.out.println("start incrementColumnValue data ......");

		Connection connection = null;
		Table table = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));

			// Use the table as needed, for a single operation and a single
			// thread

			// table.incrementColumnValue("rowkey".getBytes(),
			// "columFamily".getBytes(), "columnName".getBytes(), 1);
			table.incrementColumnValue("rowkey".getBytes(), "columFamily".getBytes(), "columnName".getBytes(), 1,
					Durability.SYNC_WAL);// 持久化策略-同步写入 预写日志 中（默认）

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("end incrementColumnValue data ......");

	}

	/**
	 * 单行上的原子操作(只是单行有效，不能跨行)
	 * 
	 * @param tableName
	 */
	public static void mutateRow(String tableName) {
		System.out.println("start mutateRow data ......");

		Connection connection = null;
		Table table = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));

			// Use the table as needed, for a single operation and a single
			// thread

			RowMutations rm = new RowMutations("rowKey".getBytes());
			Put put = new Put("rowkey".getBytes());
			put.addColumn("family".getBytes(), "qualifier".getBytes(), "value".getBytes());
			rm.add(put);
			
			Delete delete = new Delete("rowkey".getBytes());
			delete.addColumns("family".getBytes(), "qualifier".getBytes());
			rm.add(delete);

			table.mutateRow(rm);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("end mutateRow data ......");
	}

	/**
	 * 批量操作非原子性
	 * 
	 * @param tableName
	 */
	public static void batch(String tableName) {
		System.out.println("start batch data ......");

		Connection connection = null;
		Table table = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));

			// Use the table as needed, for a single operation and a single
			// thread

			List<Row> batch = new ArrayList<Row>();

			Put put = new Put("rowkey".getBytes());
			put.addColumn("columnFamily".getBytes(), "columnName".getBytes(), "columnValue".getBytes());
			batch.add(put);

			Delete delete = new Delete("rowkey".getBytes());
			batch.add(delete);

			Object[] results = new Object[batch.size()];

			table.batch(batch, results);
			for (int i = 0; i < results.length; i++) {
				System.out.println(results[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("end batch data ......");
	}

	/**
	 * 行锁 -避免使用
	 * 
	 * @param tableName
	 */
	public static void rowLock(String tableName) {
		System.out.println("start rowLock data ......");

		Connection connection = null;
		Table table = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);

			table = connection.getTable(TableName.valueOf(tableName));

			// Use the table as needed, for a single operation and a single
			// thread

			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("end rowLock data ......");
	}

}
