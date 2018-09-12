package com.ybwh.sqlparser.druid.shardingjdbc;

import org.junit.Test;

public class TestShardingJdbcSqlParser {
	
	
	@Test
	public void testSingleTableQuery() {
		String sql = "select "
				+ "id, area_name  areaName, parent_id, short_name, area_code, level, sort, id_path, name_path,is_leaf, is_del, create_time, create_id, update_time, update_id "
				+ "from area "
				+ "where id = ?";
	}
	
	
	@Test
	public void testJoinQuery() {
		String sql = "select * from emp_table t1,order t2 where t1.id=t2.id";
	}
	
	
	@Test
	public void testMutiSqlQuery(){
        String sqls = "select ID from BCP_Prize; select name from BCP_Prize";
	}
	

}
