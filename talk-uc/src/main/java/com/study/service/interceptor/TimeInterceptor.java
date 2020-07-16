package com.study.service.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("prehandle");
        System.out.println(((HandlerMethod) o).getBean() );
        System.out.println(((HandlerMethod)o).getMethod().getName());
        httpServletRequest.setAttribute("start",new Date().getTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//API 抛出异常之后，不会进入此方法
        System.out.println("postHandle");
        Long start=(long)httpServletRequest.getAttribute("start");
        System.out.println("time interceptor 耗时："+(new Date().getTime()-start));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//API 无论是否抛出异常，都会进入此方法
        System.out.println("afterCompletion");
        Long start=(long)httpServletRequest.getAttribute("start");
        System.out.println("time interceptor 耗时："+(new Date().getTime()-start));
        System.out.println("exception is :"+e);
    }
}
