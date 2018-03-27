<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setAttribute("ctx", request.getContextPath());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>验证码</title>

  </head>
  
  <body>
  <img alt="" src="${ctx }/verfyCode/verfyCode.do">
  </body>
</html>
