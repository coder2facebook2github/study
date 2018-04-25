package com.spring.boot.study.controller;

import com.spring.boot.study.common.Constants;
import com.spring.boot.study.model.master.SysUser;
import com.spring.boot.study.model.master.vo.LoginVo;
import com.spring.boot.study.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Value("${env}")
    private String env;


    @RequestMapping(value = "/")
    public String loginPage() {
        if("home".equals(env) || "company".equals(env)) {
            return "login";
        }
        if("aliyun".equals(env)) {
            return "redirect:aliyun/welcome";
        }
        return "";
    }

    @ResponseBody
    @RequestMapping(value = "/aliyun/welcome", method = RequestMethod.GET)
    public String welcomePage() {
        return "Hello world !";
    }


    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> loginIn(@RequestBody LoginVo loginVo) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> checkUser = loginService.validateUser(loginVo);
        if (!Constants.SUCCESS.equals(checkUser.get(Constants.MESSAGE))) {
            return checkUser;
        }
        SysUser user = (SysUser) checkUser.get("sysUser");
        String token = loginService.createJwt(user);
        loginService.setTokenExpiryTime(user.getId());
        result.put(Constants.MESSAGE, Constants.SUCCESS);
        result.put("token", token);
        return result;
    }

}
