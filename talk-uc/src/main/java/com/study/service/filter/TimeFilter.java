package com.study.service.filter;


import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

//@Component
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.err.println("time filter init! ");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.err.println("time filter start! ");
        long start = new Date().getTime();
        filterChain.doFilter(servletRequest, servletResponse);
        System.err.println("time filter 用时: " + (new Date().getTime() - start));
        System.err.println("time filter finish! ");
    }

    @Override
    public void destroy() {
        System.err.println("time filter destroy! ");
    }
}
