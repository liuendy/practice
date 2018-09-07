package com.ybwh.springboot2.conf;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.ybwh.springboot2.common.Constant;
import com.ybwh.springboot2.common.Response;



/**
 * 全局异常处理
 *
 * @author Fan Beibei
 * @since 1.0-SNAPSHOT
 */
@RestControllerAdvice
public class Springboot2ExceptionHandler {

	/**
	 * 处理参数异常
	 *
	 * @return
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ MissingServletRequestParameterException.class})
	public Response<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e1) {

		return new Response<Object>(Constant.FAIL_CODE, " miss parameters, "+ e1.getMessage());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({  TypeMismatchException.class })
	public Response<Object> handleMissingServletRequestParameterException(
			TypeMismatchException e2) {

		return new Response<Object>(Constant.FAIL_CODE, "param type  error, "+e2.getMessage());
	}

	/**
	 * 处理请求方式不支持异常
	 *
	 * @return
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Response<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

		return new Response<Object>(Constant.FAIL_CODE, "HTTP request type error!! required type is "
				+ e.getSupportedHttpMethods() + ",but your type is " + e.getMethod());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(NoHandlerFoundException.class)
	public Response<Object> handleNoHandlerFoundException(NoHandlerFoundException e) {
		return new Response<Object>(Constant.FAIL_CODE,
				"404  url not found!!,request type is " + e.getHttpMethod() + ",url is " + e.getRequestURL());
	}

	/**
	 * 处理其他异常
	 *
	 * @return
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(Throwable.class)
	public Response<Object> handlePlatformException(Throwable e) {
		return new Response<Object>(Constant.FAIL_CODE, ExceptionUtils.getStackTrace(e));
	}

	/**
	 * 处理文件大小限制异常
	 *
	 * @return
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MultipartException.class)
	public Response<Object> handleMultipartException(MultipartException e) {
		return new Response<Object>(Constant.FAIL_CODE, "upload file erorr!! " + ExceptionUtils.getStackTrace(e));
	}

}
