package com.spring.boot.study.common.filter;

import org.slf4j.profiler.Profiler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 计算每个请求的耗时
 */
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Profiler profiler = new Profiler("Timing filter uri: " + request.getRequestURI());
        profiler.start("request");
        request.setAttribute("profiler", profiler);
        filterChain.doFilter(servletRequest, servletResponse);
        profiler.stop();

        System.out.println(profiler.toString());
    }

    @Override
    public void destroy() {

    }
}
