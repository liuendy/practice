package com.ybwh.springboot2.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybwh.springboot2.demo.dao.AreaDao;
import com.ybwh.springboot2.demo.entity.Area;

@Service
public class AreaService {
	@Autowired
	private AreaDao areaDao;
	
	public Area selectByPrimaryKey(Integer id) {
		return areaDao.selectByPrimaryKey(id);
	}

}
