# spring 一些高级但不常用的功能


## 自定义标签
### 1.编写自定义标签 schema 定义文件，放在某个 classpath 下。
### 2.在 classpath 的在 META-INF 下面增加 spring.schemas 配置文件，指定 schema 虚拟路径和实际 xsd 的映射
### 3.增加一个 NamespaceHandler 和 BeanDefinitionParser ，用于解析自定义的标签，将自定义标签的 bean 解析成一个 BeanDefinition 返回。
### 4.在 classpath 的在 META-INF 下面增加 spring.handlers 配置文件，指定标签命名空间和 handlers 的映射。


## LifeCycle接口
LifeCycle：该接口是Spring 2.0加入的，该接口提供了start()和stop()两个方法，主要用于控制异步处理过程。在具体使用时，该接口同时被ApplicationContext实现及具体Bean实现，ApplicationContext会将start/stop的信息传递给容器中所有实现了该接口的Bean，以达到管理和控制JMX、任务调度等目的。
		  
