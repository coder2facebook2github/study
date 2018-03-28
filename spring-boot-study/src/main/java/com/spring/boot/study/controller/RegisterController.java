package com.spring.boot.study.controller;


import com.aliyuncs.exceptions.ClientException;
import com.spring.boot.study.common.Constants;
import com.spring.boot.study.common.SendSmsUtils;
import com.spring.boot.study.model.SysUser;
import com.spring.boot.study.service.RegisterService;
import com.utils.JedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private SendSmsUtils sendSmsUtils;
    @Autowired
    private JedisService jedisService;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map<String, Object> register(@RequestBody SysUser user, String validateCode) {
        Map<String, Object> result = new HashMap<>();
        boolean checkCode = registerService.checkValidateCode(user.getMobile(), validateCode);
        if(!checkCode) {
            result.put("message", "验证吗错误");
        } else {
            registerService.saveUser(user);
            result.put("message", "注册成功");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/get/validate/code", method = RequestMethod.POST)
    public Map<String, Object> getValidateCode(String mobile, HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if(StringUtils.isBlank(mobile)) {
            result.put("message", "手机号为空");
            return result;
        }
        try {
            sendSmsUtils.sendIdentifyCode(mobile);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        result.put("message", "success");
        return result;
    }
}
