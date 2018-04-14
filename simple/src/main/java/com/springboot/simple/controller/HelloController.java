package com.springboot.simple.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping(value = "/")
    @ResponseBody
    public String hello() {
        return "Hello Aliyun";
    }
}
