package com.spring.boot.study.controller;


import com.spring.boot.study.model.Areas;
import com.spring.boot.study.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SysController {

    @Autowired
    private SysService sysService;

    @ResponseBody
    @RequestMapping(value = "/area", method = RequestMethod.GET)
    public Map<String, Object> getArea(String name) {
        Map<String, Object> result = new HashMap<>();
        Areas areas = sysService.getAreaByName(name);
        System.out.println(areas);
        result.put("city", areas);
        return result;
    }
}
