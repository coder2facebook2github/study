package com.spring.boot.study.controller;


import com.aliyuncs.exceptions.ClientException;
import com.spring.boot.study.common.Constants;
import com.spring.boot.study.common.exception.BootStudyException;
import com.spring.boot.study.common.utils.SendSmsUtils;
import com.spring.boot.study.model.master.SysUser;
import com.spring.boot.study.model.master.vo.RegisterVo;
import com.spring.boot.study.service.LoginService;
import com.spring.boot.study.service.RegisterService;
import com.utils.JedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private SendSmsUtils sendSmsUtils;
    @Autowired
    private LoginService loginService;
    @Autowired
    private JedisService jedisService;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map<String, Object> register(@RequestBody @Validated RegisterVo user) {
        Map<String, Object> result = new HashMap<>();
        boolean checkCode = registerService.checkValidateCode(user.getMobile(), user.getValidateCode());
        Map<String, String> checkUser = registerService.validateUser(user);
        if(!Constants.SUCCESS.equals(checkUser.get(Constants.MESSAGE))) {
            result.put(Constants.MESSAGE, checkUser.get(Constants.MESSAGE));
        } else if(!checkCode) {
            result.put(Constants.MESSAGE, "验证码错误");
        } else {
            registerService.saveUser(user);
            result.put(Constants.MESSAGE, "注册成功");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/get/message/code/noLogin", method = RequestMethod.POST)
    public Map<String, Object> getValidateCode(String mobile, HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if(StringUtils.isBlank(mobile)) {
            result.put(Constants.MESSAGE, "手机号为空");
            return result;
        }
        SysUser user = loginService.getSysUserByMobile(mobile);
        if(user == null) {
            result.put(Constants.MESSAGE, "手机号不存在");
            return result;
        }
        try {
            sendSmsUtils.sendValidateCode(mobile);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        result.put(Constants.MESSAGE, Constants.SUCCESS);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/valid/message/code/noLogin", method = RequestMethod.POST)
    public Map<String, Object> validMessageCode(String mobile, String code) {
        Map<String, Object> result = new HashMap<>();
        String redisCode = jedisService.getStr(Constants.MESSAGE_CODE + mobile);
        if(StringUtils.isBlank(redisCode) || !redisCode.equals(code)) {
            result.put("error", "验证码错误");
        }
        return result;
    }

    @RequestMapping(value = "/change/password/noLogin", method = RequestMethod.POST)
    public String changePassword(String mobile, String password, String code, RedirectAttributes redirectAttributes) {
        String redisCode = jedisService.getStr(Constants.MESSAGE_CODE + mobile);
        if(StringUtils.isBlank(redisCode) || !redisCode.equals(code)) {
            throw new BootStudyException("验证码错误");
        }
        registerService.changePassword(mobile, password);
        redirectAttributes.addFlashAttribute("mobile", mobile);
        return "redirect:/";
    }
}
