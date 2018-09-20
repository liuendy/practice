package com.ybwh.springboot1.conf;

import java.util.List;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ybwh.springboot1.conf.intercepter.InnerApiHandlerInterceptor;

/**
 * spring mvc 的配置
 */
@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters
                .removeIf(httpMessageConverter -> httpMessageConverter instanceof MappingJackson2HttpMessageConverter); // 删除MappingJackson2HttpMessageConverter

        converters.add(new FastJsonHttpMessageConverter()); // 添加 FastJsonHttpMessageConverter
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InnerApiHandlerInterceptor());
    }
    
    
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //setUseSuffixPatternMatch 后缀模式匹配
        configurer.setUseSuffixPatternMatch(false);
        //setUseTrailingSlashMatch 自动后缀路径模式匹配
        configurer.setUseTrailingSlashMatch(true);
    }
    
    @Bean
	public DispatcherServlet dispatcherServlet() {
		DispatcherServlet dispatcherServlet = new DispatcherServlet();
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		return dispatcherServlet;
	}
    
}