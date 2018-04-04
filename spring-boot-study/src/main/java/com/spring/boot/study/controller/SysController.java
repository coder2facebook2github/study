package com.spring.boot.study.controller;


import com.spring.boot.study.common.ConstantConfig;
import com.spring.boot.study.model.Areas;
import com.spring.boot.study.model.SysUser;
import com.spring.boot.study.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SysController {

    @Autowired
    private SysService sysService;
    @Autowired
    private ConstantConfig constantConfig;
    @Value("${spring.hello.test}")
    String test;

    @ResponseBody
    @RequestMapping(value = "/area/{id}", method = RequestMethod.GET)
    public Map<String, Object> getArea(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        Areas areas = sysService.getAreaById(id);
        System.out.println(areas);
        result.put("city", areas);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/constant/config")
    public Map<String, Object> constantConfig() {
        Map<String, Object> result = new HashMap<>();
        result.put("constant", constantConfig);
        System.out.println("test: " + test);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/sys/user")
    public Map<String, Object> userTest(SysUser user, String name) {
        Map<String, Object> result = new HashMap<>();

        result.put("user", user);
        return result;
    }

}
