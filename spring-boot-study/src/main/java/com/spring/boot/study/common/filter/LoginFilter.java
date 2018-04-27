package com.spring.boot.study.common.filter;

import com.spring.boot.study.common.Constants;
import com.spring.boot.study.common.exception.LoginException;
import com.spring.boot.study.service.LoginService;
import com.utils.JedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 已废弃，被LoginInterceptor代替
 */
//@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
public class LoginFilter implements Filter {

    private Set<String> excludeUris;
    @Autowired
    private FilterConfiguration filterConfiguration;
    @Autowired
    private LoginService loginService;
    @Autowired
    private JedisService jedisService;
    @Value("${token.timeout}")
    private Integer timeout;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludeUris = filterConfiguration.getUri();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorizationHeader = request.getHeader(Constants.AUTHORIZATION);
        String requestURI = request.getRequestURI().replaceAll("/+$", "")
                .replaceAll("/+", "/");
        System.out.println(requestURI);
        long userId = 0;
        if("".equalsIgnoreCase(requestURI) || checkExcludeUri(requestURI)) {
            System.out.println("无需验证登陆");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            if(StringUtils.isBlank(authorizationHeader)) {
                throw new LoginException("无权访问", 401);
            }
            try {
                userId = loginService.parseJwt(authorizationHeader);
            } catch (Exception e) {
                e.printStackTrace();
                throw new LoginException("无权访问", 401);
            }
            try {
                long lastTime = jedisService.get(Constants.USER_TOKEN + userId);
            } catch (NullPointerException e) {
                e.printStackTrace();
                throw new LoginException("登陆已过期，请重新登陆", 401);
            }
            jedisService.set(Constants.USER_TOKEN + userId, System.currentTimeMillis(), timeout);
        }

        System.out.println("登陆验证通过");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean checkExcludeUri(String uri) {
        for(String excludeUri : excludeUris) {
            if(!excludeUri.contains("*")) {
                if(excludeUri.equals(uri)) {
                    return true;
                }
            } else {
                Pattern pattern = Pattern.compile("^(" + excludeUri.replaceAll("\\**$", "") + ")\\S*$");
                Matcher matcher = pattern.matcher(uri);
                if(matcher.matches()) {
                    return true;
                }
            }
        }
        return false;
    }
}
