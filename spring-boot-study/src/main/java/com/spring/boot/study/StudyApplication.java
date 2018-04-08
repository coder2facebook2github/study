package com.spring.boot.study;

import com.spring.boot.study.common.argument.resolver.LoginUserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@ServletComponentScan(basePackages = "com.spring.boot.study.common")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StudyApplication implements WebMvcConfigurer {

    @Autowired
    private LoginUserResolver loginUserResolver;

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserResolver);
    }
}
