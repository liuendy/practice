package com.ybwh.activiti5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;

public class ProcessEngineTest {
    @Test
    public void createProcessEngine1() {
        try {
			String resource = "activiti-context.xml";    // 配置文件
			String beanName = "processEngineConfiguration";  // 配置文件中bean name
			// 从配置文件创建配置对象
			ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(resource, beanName);
			// 根据配置创建引擎对象
			ProcessEngine processEngine = config.buildProcessEngine();
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     *  一条语句创建processEngine, 要求:
     * 1、配置文件必须在classpath根目录下
     * 2、配置文件名必须为activiti-context.xml或activiti.cfg.xml
     * 3、工厂对象的id必须为processEngine
     */
//    @Test
    public void createProcessEngine2() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    }
    
    
    /**
     * 部署流程定义 
     * 一套定义文件只有一个流程定义Key, 但可以被部署多次形成多个版本(部署表里多个id和流程定义表里多个id)
     * 涉及的表：act_re_deployment(部署表)、act_re_procdef(流程定义表)、act_ge_bytearray(二进制表)
     */
    @Test
    public void test() throws FileNotFoundException {
    	String resource = "activiti-context.xml";    // 配置文件
		String beanName = "processEngineConfiguration";  // 配置文件中bean name
		// 从配置文件创建配置对象
		ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(resource, beanName);
		// 根据配置创建引擎对象
		ProcessEngine processEngine = config.buildProcessEngine();
		
        DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService().createDeployment();
        // 逐个文件部署
        // deploymentBuilder.addClasspathResource("qjlc.bpmn");
        // deploymentBuilder.addClasspathResource("qjlc.png");
        // 压缩文件打包部署, 推荐
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(new File("d:\\processDef.zip")));
        deploymentBuilder.addZipInputStream(zipInputStream );
        
        Deployment deployment = deploymentBuilder.deploy();
    }
}