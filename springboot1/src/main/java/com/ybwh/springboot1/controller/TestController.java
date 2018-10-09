package com.ybwh.springboot1.controller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ybwh.springboot1.common.Constant;
import com.ybwh.springboot1.common.Response;

@RestController
@RequestMapping("/")
public class TestController {

	@PostMapping(value = "/test")
	@ResponseBody
	public Response<Object> test(@RequestBody String data) {
		System.out.println(data);

		return new Response<Object>(Constant.FAIL_CODE, " test response");

	}
	
	
	@RequestMapping(value = "/testDate")
	@ResponseBody
	public Response<Object> testDate(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
		System.out.println(date);

		return new Response<Object>(Constant.FAIL_CODE, " test response");

	}

}
