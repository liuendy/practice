package com.common.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.common.annotation.LoginRequired;

/**
 * @author Fan Beibei at 2014-5-26 <br/>
 * last modefied by Fan Beibei at 2014-5-26
 */
public class LoginInterceptor extends  HandlerInterceptorAdapter
{
	private static Logger logger = Logger.getLogger(LoginInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception
	{
		logger.debug("LoginInterceptor  execute");
		
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;

			// 登录判断
			LoginRequired loginRequired  = handlerMethod.getMethodAnnotation(LoginRequired.class);
			if (null == loginRequired) {
				loginRequired = handlerMethod.getBeanType().getAnnotation(LoginRequired.class);
			}

			if (null != loginRequired) {
				logger.debug("login required");
				//判断是否已登录,然后做出处理
				
				
			}
		}
		return true;
	}


}
