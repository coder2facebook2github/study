package com.spring.boot.study;

import com.spring.boot.study.common.listener.LoadPropertiesListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StudyApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(StudyApplication.class);
        /**
         * 这个Listener必须写在这儿，不能用@Bean注解，因为这个Listener是加载配置文件必须优先加载
         */
        springApplication.addListeners(new LoadPropertiesListener());
        springApplication.run(args);
    }
}
