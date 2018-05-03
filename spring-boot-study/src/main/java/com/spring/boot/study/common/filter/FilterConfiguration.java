package com.spring.boot.study.common.filter;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashSet;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "login.exclude")
public class FilterConfiguration {


    Set<String> uri = new HashSet<>();

    public Set<String> getUri() {
        return uri;
    }

    public void setUri(Set<String> uri) {
        this.uri = uri;
    }

    /**
     * 这是必须的，loginFilter里注入有其他的Bean
     */
//    @Bean("loginFilter")
//    public LoginFilter loginFilter() {
//        return new LoginFilter();
//    }

    /**
     * 这种方式能够决定Filter的执行顺序，@WebFilter的方式不能决定Filter的执行顺序
     * spring-boot @WebFilter not support @Order
     * https://github.com/spring-projects/spring-boot/issues/8276
     */
//    @Bean("loginFilterRegister")
//    public FilterRegistrationBean loginFilterRegister(LoginFilter loginFilter) {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(loginFilter);
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setName("loginFilter");
//        registrationBean.setOrder(Integer.MAX_VALUE);
//        return registrationBean;
//    }

    /**
     * 这是非必须的
     */
    @Bean("requestContentFilter")
    public RequestContentFilter requestContentFilter() {
        return new RequestContentFilter();
    }

    @Bean("requestContentFilterRegister")
    public FilterRegistrationBean requestContentFilterRegister(RequestContentFilter requestContentFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(requestContentFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("requestContentFilter");
        registrationBean.setOrder(Integer.MAX_VALUE - 1);
        return registrationBean;
    }

    @Bean("timeFilter")
    public TimeFilter timeFilter() {
        return new TimeFilter();
    }

    @Bean("timeFilterRegister")
    public FilterRegistrationBean timeFilterRegister(TimeFilter timeFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(timeFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("timeFilter");
        registrationBean.setOrder(Integer.MAX_VALUE - 2);
        return registrationBean;
    }



}
