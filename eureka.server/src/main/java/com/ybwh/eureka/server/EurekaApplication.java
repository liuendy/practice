package com.ybwh.eureka.server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;  
  
/** 
 * 运行Eureka服务器 
 * @author pangps 
 */  
@SpringBootApplication // Spring Boot 应用标识  
@EnableEurekaServer // Eureka Server 标识并会自动化读取相关配置。  
public class EurekaApplication {  
    public static void main(String[] args) {
        // 程序启动入口  
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件  
        SpringApplication.run(EurekaApplication.class, args);  
    }  
}