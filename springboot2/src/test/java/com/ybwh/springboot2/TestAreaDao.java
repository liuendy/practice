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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybwh.springboot2.demo.dao.AreaDao;
import com.ybwh.springboot2.demo.entity.Area;


@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class TestAreaDao {
	Logger logger = LoggerFactory.getLogger("TTTTTTTTTTTTTTT");
	@Autowired
	AreaDao dao;

	@Test
	public void testSelect() {
		logger.info("**************************************************");
		
		try {
			Assert.assertNotNull(dao);
			//BaseMapper自带方法
			Area a= dao.selectById(130121);
			System.out.println(a);
			//xml的方法
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
			
			//BaseMapper自带的分页查询
			Page<Area> pageParam = new Page<>();
			//设置分页参数
			pageParam.setCurrent(2);
			pageParam.setSize(10);
			
			QueryWrapper<Area> queryWrapper =  new QueryWrapper<>();
			//设置查询条件
			queryWrapper.eq("parent_id", 130100);
			//设置不需要返回的列列
			queryWrapper.excludeColumns(Area.class,"short_name","area_code");
			IPage<Area> pageReslut = dao.selectPage(pageParam, queryWrapper);
			System.out.println(pageReslut);
			
			
			//xml自定义sql的分页
			Page<Area> pageParam2 = new Page<>();
			//设置分页参数
			pageParam2.setCurrent(2);
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
