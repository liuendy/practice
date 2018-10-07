package com.ybwh.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/config")
public class ConfigController {

	/**
	 * 通过 Spring 的 @Value 注解设置属性值。 注意：需要同时有 Setter方法才能在配置变更的时候自动更新。
	 */
	@Value("${useLocalCache:false}")
	private boolean useLocalCache;

	public void setUseLocalCache(boolean useLocalCache) {
		this.useLocalCache = useLocalCache;
	}

	@RequestMapping(value = "/get")
	@ResponseBody
	public boolean get() {
		return useLocalCache;
	}
}