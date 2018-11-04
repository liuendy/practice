package com.ybwh.springboot2.conf;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.ybwh.springboot2.common.mybatis.plugin.update.UpdateInterceptor;
import com.ybwh.springboot2.common.mybatis.plugin.update.UpdateInterceptor2;

/**
 * Created by jackl on 2017/2/13.
 */
@Configuration
public class MyBatisPlusConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.ybwh.springboot2.**.**.dao");
        return mapperScannerConfigurer;
    }
    
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    
//    @Bean
    public UpdateInterceptor updateInterceptor() {
        return new UpdateInterceptor();
    }
    @Bean
    public UpdateInterceptor2 updateInterceptor2() {
        return new UpdateInterceptor2();
    }

}