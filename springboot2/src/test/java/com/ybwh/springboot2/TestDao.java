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
			//BaseMapper�Դ���ѯ
			Area a= dao.selectById(130121);
			System.out.println(a);
			//�Զ����ѯ
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
			 * ��������BaseMapper�Դ���ҳ��ѯ���趨����̫���ӣ�̫�����
			 */
			
			//BaseMapper�Դ���ҳ��ѯ
			Page<Area> pageParam = new Page<>();
			//��ǰҳ
			pageParam.setCurrent(2);
			//ҳ��С
			pageParam.setSize(10);
			QueryWrapper<Area> queryWrapper =  new QueryWrapper<>();
			//��ѯ����
			queryWrapper.eq("parent_id", 130100);
			//�ų�����Ҫ����
			queryWrapper.excludeColumns(Area.class,"short_name","area_code");
			IPage<Area> pageReslut = dao.selectPage(pageParam, queryWrapper);
			System.out.println(pageReslut);
			
			
			//�Զ����ҳ��ѯ
			Page<Area> pageParam2 = new Page<>();
			//��ǰҳ
			pageParam2.setCurrent(2);
			//ҳ��С
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
