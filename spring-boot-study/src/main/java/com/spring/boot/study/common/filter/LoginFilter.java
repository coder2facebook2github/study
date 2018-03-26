package com.spring.boot.study.common.filter;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
public class LoginFilter implements Filter {

    private Set<String> excludeUris;
    @Autowired
    private FilterConfiguration filterConfiguration;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludeUris = filterConfiguration.getUri();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI().replaceAll("/+$", "")
                .replaceAll("/+", "/");
        System.out.println(requestURI);
        if(checkExcludeUri(requestURI)) {
            System.out.println("无需验证登陆");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            
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
