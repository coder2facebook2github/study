package com.spring.boot.study.service;


import com.spring.boot.study.common.Constants;
import com.spring.boot.study.model.master.vo.SendMailVo;
import com.utils.JedisService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JedisService jedisService;

    private String validCodeMailContent = "您的验证码是: $code";


    /**
     * 发送简单文本邮件
     */
    public void sendSimpleMail(SendMailVo mailVo) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(mailVo.getSendTo());
        mailMessage.setSubject(mailVo.getTitle());
        mailMessage.setText(mailVo.getContent());
        javaMailSender.send(mailMessage);
    }

    /**
     * 发送邮箱验证码
     */
    public void sendValidCode(SendMailVo mailVo) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(mailVo.getSendTo());
        mailMessage.setSubject("验证码");
        String code = RandomStringUtils.randomNumeric(6);
        mailMessage.setText(validCodeMailContent.replace("$code", code));
        javaMailSender.send(mailMessage);
        jedisService.set(Constants.MAIL_CODE + mailVo.getSendTo(), code);

    }

}
