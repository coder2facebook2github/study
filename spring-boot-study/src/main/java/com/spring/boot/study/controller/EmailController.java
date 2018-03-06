package com.spring.boot.study.controller;


import com.spring.boot.study.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @ResponseBody
    @RequestMapping(value = "/simple/mail", method = RequestMethod.GET)
    public Map<String, Object> simpleEmail(String sendTo, String title, String content) {
        Map<String, Object> result = new HashMap<>();
        emailService.sendSimpleMail(sendTo, title, content);
        result.put("message", "success");
        return result;
    }



}
