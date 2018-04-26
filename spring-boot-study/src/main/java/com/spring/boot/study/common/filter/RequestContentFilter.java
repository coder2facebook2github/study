package com.spring.boot.study.common.filter;

import com.spring.boot.study.common.RepeatedlyReadRequestWrapper;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

//@WebFilter(urlPatterns = "/*", filterName = "requestContentFilter")
public class RequestContentFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request = new RepeatedlyReadRequestWrapper(request);
        InputStream inputStream = request.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        int len = request.getContentLength();
        if(len < 0) {
            filterChain.doFilter(request, servletResponse);
            return;
        }
        byte[] totalContent = new byte[len];
        bufferedInputStream.read(totalContent);
        bufferedInputStream.close();
        String requestContent = new String(totalContent);
        System.out.println(requestContent);
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
