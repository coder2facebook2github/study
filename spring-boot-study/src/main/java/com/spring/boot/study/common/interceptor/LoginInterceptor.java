package com.spring.boot.study.common.interceptor;


import com.spring.boot.study.common.Constants;
import com.spring.boot.study.common.exception.LoginException;
import com.spring.boot.study.service.LoginService;
import com.utils.JedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;
    @Autowired
    private JedisService jedisService;
    @Value("${token.timeout}")
    private Integer timeout;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authorizationHeader = request.getHeader(Constants.AUTHORIZATION);
        String requestURI = request.getRequestURI().replaceAll("/+$", "")
                .replaceAll("/+", "/");
        System.out.println(requestURI);
        long userId = 0;

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
            String token = jedisService.get(Constants.USER_TOKEN + userId);
            if(StringUtils.isBlank(token) || !token.equals(authorizationHeader)) {
                throw new LoginException("token失效", 401);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new LoginException("登陆已过期，请重新登陆", 401);
        }
        jedisService.set(Constants.USER_TOKEN + userId, System.currentTimeMillis(), timeout);
        System.out.println("interceptor============== 登陆验证通过");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
