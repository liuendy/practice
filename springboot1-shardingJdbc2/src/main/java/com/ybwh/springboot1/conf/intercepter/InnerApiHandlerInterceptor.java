package com.ybwh.springboot1.conf.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 内部接口的拦截器
 *
 * @author: Fan Beibei
 * @date: 2018/5/4 17:56
 * @Modified By:
 */
public class InnerApiHandlerInterceptor implements HandlerInterceptor {



    private static Logger logger = LoggerFactory.getLogger(InnerApiHandlerInterceptor.class);

    private static final String INTERNAL_URL_PATH = "/internal";


//    @Value("${ehr.service.key}")
//    private String serviceKey;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {//拦截controller方法
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequestMapping requestMappingOnClass = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
            String pathOnClass = requestMappingOnClass.value()[0];

            if (INTERNAL_URL_PATH.equals(pathOnClass)) {





            }



        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
