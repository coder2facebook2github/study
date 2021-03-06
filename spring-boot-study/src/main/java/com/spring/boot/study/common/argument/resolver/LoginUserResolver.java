package com.spring.boot.study.common.argument.resolver;

import com.spring.boot.study.common.Constants;
import com.spring.boot.study.common.exception.LoginException;
import com.spring.boot.study.dao.master.sys.SysUserDao;
import com.spring.boot.study.model.master.SysUser;
import com.spring.boot.study.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(SysUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        long userId = 0;
        String authorizationHeader = nativeWebRequest.getHeader(Constants.AUTHORIZATION);
        if (StringUtils.isBlank(authorizationHeader)) {
            throw new LoginException("无权访问，请先登录", 401);
        }
        try {
            userId = loginService.parseJwt(authorizationHeader);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LoginException("无权访问，请先登录", 401);
        }
        SysUser user = sysUserDao.selectByPrimaryKey(userId);
        return user;
    }
}
