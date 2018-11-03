package com.ybwh.springboot2;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybwh.springboot2.demo.dao.AreaDao;
import com.ybwh.springboot2.demo.entity.Area;


@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class TestDao {
	Logger logger = LoggerFactory.getLogger("TTTTTTTTTTTTTTT");
	@Autowired
	AreaDao dao;

	@Test
	public void testSelect() {
		logger.info("**************************************************");
		
		try {
			Assert.assertNotNull(dao);
			//BaseMapper自带查询
			Area a= dao.selectById(130121);
			System.out.println(a);
			//自定义查询
			Area a2= dao.selectByPrimaryKey(130121);
			System.out.println(a2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSelectPage() {
		logger.info("**************************************************");
		
		try {
			Assert.assertNotNull(dao);
			/**
			 * 不建议用BaseMapper自带分页查询，设定条件太复杂，太不灵活
			 */
			
			//BaseMapper自带分页查询
			Page<Area> pageParam = new Page<>();
			//当前页
			pageParam.setCurrent(2);
			//页大小
			pageParam.setSize(10);
			QueryWrapper<Area> queryWrapper =  new QueryWrapper<>();
			//查询条件
			queryWrapper.eq("parent_id", 130100);
			//排除不需要的列
			queryWrapper.excludeColumns(Area.class,"short_name","area_code");
			IPage<Area> pageReslut = dao.selectPage(pageParam, queryWrapper);
			System.out.println(pageReslut);
			
			
			//自定义分页查询
			Page<Area> pageParam2 = new Page<>();
			//当前页
			pageParam2.setCurrent(2);
			//页大小
			pageParam2.setSize(10);
			Map<String,Object> param = new HashMap<>();
			param.put("parentId", 130100);
			
			IPage<Area> pageReslut2 = dao.selectPageVo(pageParam2 , param);
			System.out.println(pageReslut2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
