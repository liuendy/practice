package com.ybwh.springboot2.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ybwh.springboot2.demo.entity.Area;
import com.ybwh.springboot2.demo.service.AreaService;

@RestController
@RequestMapping("/area")
public class AreaController {
	@Autowired
	private AreaService areaService;
	
	@RequestMapping("/test")
	@ResponseBody
	public Area test() {
		return areaService.selectByPrimaryKey(110102);
	}

}
