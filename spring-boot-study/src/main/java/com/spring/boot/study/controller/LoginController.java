package com.spring.boot.study.controller;

import com.spring.boot.study.common.Constants;
import com.spring.boot.study.model.master.SysUser;
import com.spring.boot.study.model.master.vo.LoginVo;
import com.spring.boot.study.service.LoginService;
import com.utils.JedisService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Value("${env}")
    private String env;
    @Autowired
    private JedisService jedisService;


    @RequestMapping(value = "/")
    public String loginPage(Model model) {
        String token = DigestUtils.md5Hex(UUID.randomUUID().toString() + new Random().nextLong());
        model.addAttribute("randomImageUrl", "/image/random/noLogin?token=" + token);
        model.addAttribute("token", token);
        if("home".equals(env) || "company".equals(env)) {
            return "login";
        }
        if("aliyun".equals(env)) {
            return "redirect:welcome";
        }
        return "";
    }

    @ResponseBody
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomePage() {
        return "Hello world !";
    }


    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> loginIn(@RequestBody @Validated LoginVo loginVo) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> checkImageCode = loginService.validateImageCode(loginVo);
        if(!Constants.SUCCESS.equals(checkImageCode.get(Constants.MESSAGE))) {
            return checkImageCode;
        }
        Map<String, Object> checkUser = loginService.validateUser(loginVo);
        if (!Constants.SUCCESS.equals(checkUser.get(Constants.MESSAGE))) {
            return checkUser;
        }
        SysUser user = (SysUser) checkUser.get("sysUser");
        String token = loginService.createJwt(user);
        loginService.setTokenExpiryTime(user.getId());
        result.put(Constants.MESSAGE, Constants.SUCCESS);
        jedisService.del(Constants.IMAGE_TOKEN + loginVo.getToken());
        result.put("token", token);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/check/mobile/exist/noLogin", method = RequestMethod.POST)
    public Map<String, Object> checkMobileExist(String mobile) {
        Map<String, Object> validResult = new HashMap<>();
        SysUser user = loginService.getSysUserByMobile(mobile);
        if(user == null) {
            validResult.put("error", "手机号不存在");
        }
        return validResult;
    }

    @ResponseBody
    @RequestMapping(value = "/check/password/noLogin", method = RequestMethod.POST)
    public Map<String, Object> checkPassword(String mobile, String password) {
        Map<String, Object> validResult = new HashMap<>();
        boolean checkResult = loginService.checkPassword(mobile, password);
        if(!checkResult) {
            validResult.put("error", "密码错误");
        }
        return validResult;
    }

}
