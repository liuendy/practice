package com.common.resolver;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SimpleMethodArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//返回false表示不经过resolveArgument处理
		if("data".equals(parameter.getParameterName())) {
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String data = webRequest.getParameter(parameter.getParameterName());
		System.out.println("*************data="+data);
		data = data+"*******************";
		
		// 将解析后的值绑定到对应的Controller参数上，利用DataBinder提供的方法便捷的实现类型转换
	    if (binderFactory != null) {
	        
	        // 生成参数绑定器，第一个参数为request请求对象，第二个参数为需要绑定的目标对象，第三个参数为需要绑定的目标对象名
	        WebDataBinder binder = binderFactory.createBinder(webRequest, null, parameter.getParameterName());
	        
	        try {

	            // 将参数转到预期类型，第一个参数为解析后的值，第二个参数为绑定Controller参数的类型，第三个参数为绑定的Controller参数
	        	data = (String) binder.convertIfNecessary(data, parameter.getParameterType(), parameter);
	        
	        } catch (ConversionNotSupportedException ex) {
//	            throw new MethodArgumentConversionNotSupportedException(data, ex.getRequiredType(),
//	                    parameter.getParameterName(), parameter, ex.getCause());
	        } catch (TypeMismatchException ex) {
//	            throw new MethodArgumentTypeMismatchException(data, ex.getRequiredType(),
//	                    parameter.getParameterName(), parameter, ex.getCause());
	        }
	    }
		return data;
	}



}
