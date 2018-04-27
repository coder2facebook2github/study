package com.spring.boot.study.common.configuration;

import com.spring.boot.study.common.utils.SysConfigurationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Autowired
    private SysConfigurationUtils sysConfigurationUtils;

    @Bean("javaMailSender")
    @ConfigurationProperties("mail")
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setPassword(sysConfigurationUtils.getString("wang_yi_pop_shouquanma"));
        return mailSender;
    }
}
