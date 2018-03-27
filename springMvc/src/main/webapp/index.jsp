<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setAttribute("ctx", request.getContextPath());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    This is my springMVC page. <br>
    <ul>
    	<li><a href="${ctx }/demo/hello.do" target="_blank">demo hello</a></li>
    	<li><a href="${ctx }/demo/validate.do" target="_blank">Hibernate-Validator后台实体验证</a></li>
    	<li><a href="${ctx}/" target="_blank">日期格式问题</a></li>
    	<li><a href="${ctx }/demo/modelDriver.do?demo.name=trwetg&demo.age=345" target="_blank">模型驱动</a></li>
    	<li><a href="${ctx }/verfyCode.jsp" target="_blank">验证码 </a></li>
    	<li><a href="" target="_blank"></a></li>
    </ul>
  </body>
</html>
