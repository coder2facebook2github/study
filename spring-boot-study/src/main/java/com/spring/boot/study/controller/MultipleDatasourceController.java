package com.spring.boot.study.controller;

import com.spring.boot.study.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class MultipleDatasourceController {

    @Autowired
    private SysService sysService;

    @ResponseBody
    @RequestMapping(value = "/multiple/user")
    public Map<String, Object> getObject(String mobile) {
        return sysService.getByMobile(mobile);
    }

}
