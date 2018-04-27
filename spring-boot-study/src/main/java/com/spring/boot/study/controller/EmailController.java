package com.spring.boot.study.controller;


import com.spring.boot.study.common.validate.group.GroupA;
import com.spring.boot.study.common.validate.group.GroupB;
import com.spring.boot.study.model.master.vo.SendMailVo;
import com.spring.boot.study.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
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
    @RequestMapping(value = "/send/simple/mail", method = RequestMethod.POST)
    public Map<String, Object> simpleEmail(@Validated(GroupA.class) @RequestBody SendMailVo mailVo) {
        Map<String, Object> result = new HashMap<>();
        emailService.sendSimpleMail(mailVo);
        result.put("message", "success");
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/send/valid/code", method = RequestMethod.POST)
    public Map<String, Object> sendMailValidCode(@Validated(GroupB.class) @RequestBody SendMailVo mailVo) {
        Map<String, Object> result = new HashMap<>();
        emailService.sendValidCode(mailVo);
        result.put("message", "success");
        return result;
    }



}
