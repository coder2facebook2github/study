package com.spring.boot.study.controller;


import com.spring.boot.study.common.ConstantConfig;
import com.spring.boot.study.common.exception.LoginException;
import com.spring.boot.study.common.filter.FilterConfiguration;
import com.spring.boot.study.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Controller
@Validated
public class TestController {


    @Value("${spring.hello.test}")
    private String hello;
    @Autowired
    private ConstantConfig constantConfig;

    @Autowired
    private FilterConfiguration filterConfiguration;

    @ResponseBody
    @RequestMapping(value = "/hello/test")
    public Map<String, Object> test(String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("hello", hello);
        result.put("唐诗", "枯藤老树昏鸦");
        result.put("constant", constantConfig);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/constant/test")
    public Map<String, Object> testConstantConfig(@Valid SysUser user) {
        Map<String, Object> result = new HashMap<>();
        System.out.println(constantConfig);
        result.put("config", constantConfig);
        result.put("urls", filterConfiguration.getUri());
        return result;
    }

    @RequestMapping(value = "/exception")
    public String test() {
        int a = 3/0;
        return "test";
    }

    @RequestMapping(value = "/valid")
    @ResponseBody
    public Map<String, Object> testValid(@NotBlank(message = "name:名字不能为空")String name,
                                         @NotBlank(message = "mobile:手机号不能为空") String mobile) {
        Map<String, Object> result = new HashMap<>();


        return result;
    }


}
