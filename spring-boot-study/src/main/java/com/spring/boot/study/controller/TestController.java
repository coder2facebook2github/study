package com.spring.boot.study.controller;

import com.spring.boot.study.common.ConstantConfig;
import com.spring.boot.study.common.filter.FilterConfiguration;
import com.spring.boot.study.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {


    @Value("${spring.hello.test}")
    private String hello;
    @Autowired
    private ConstantConfig constantConfig;

    @Autowired
    private FilterConfiguration filterConfiguration;

    @ResponseBody
    @RequestMapping(value = "/hello/test")
    public Map<String, Object> test(String name, SysUser user) {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("hello", hello);
        result.put("唐诗", "枯藤老树昏鸦");
        result.put("constant", constantConfig);
        result.put("user", user);
        result.put("filterConfiguration", filterConfiguration.getUri());
        return result;
    }

    @RequestMapping(value = "/exception")
    public String test() {
        int a = 3 / 0;
        return "test";
    }
}
