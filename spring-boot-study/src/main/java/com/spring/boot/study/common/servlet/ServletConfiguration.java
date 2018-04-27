package com.spring.boot.study.common.servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfiguration {

    @Bean("sysConfigInitServlet")
    public ServletRegistrationBean sysConfigInitServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new SysConfigInitServlet());
        registrationBean.addUrlMappings("/sys/config/init");
        return registrationBean;
    }

}
