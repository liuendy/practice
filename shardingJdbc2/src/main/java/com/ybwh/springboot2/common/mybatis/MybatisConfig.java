package com.ybwh.springboot2.common.mybatis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ybwh.springboot2.common.mybatis.plugin.ResultSetInterceptor;

/**
 * mybatis的拦截器配置
 *
 * @author: Fan Beibei
 * @date: 2018/9/4 11:48
 * @Modified By:
 */
//@Configuration
public class MybatisConfig {


//    @Bean
    public ResultSetInterceptor sqlStatsInterceptor(){
        ResultSetInterceptor interceptor = new ResultSetInterceptor();

        return interceptor;
    }
}
