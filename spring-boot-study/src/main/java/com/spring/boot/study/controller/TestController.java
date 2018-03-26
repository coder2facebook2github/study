package com.spring.boot.study.controller;


import com.spring.boot.study.common.ConstantConfig;
import com.spring.boot.study.common.filter.FilterConfiguration;
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
    public Map<String, Object> testConstantConfig() {
        Map<String, Object> result = new HashMap<>();
        System.out.println(constantConfig);
        result.put("config", constantConfig);
        result.put("urls", filterConfiguration.getUri());
        return result;
    }


}
