package com.spring.boot.study.controller;


import com.spring.boot.study.common.ConstantConfig;
import com.spring.boot.study.common.JedisService;
import com.spring.boot.study.model.Areas;
import com.spring.boot.study.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
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
    @Autowired
    private JedisService jedisService;

    @ResponseBody
    @RequestMapping(value = "/area", method = RequestMethod.GET)
    public Map<String, Object> getArea(String name) {
        Map<String, Object> result = new HashMap<>();
        Areas areas = sysService.getAreaByName(name);
        System.out.println(areas);
        result.put("city", areas);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    public Map<String, Object> path() {
        Map<String, Object> result = new HashMap<>();
        //这种方式在项目以jar包方式运行，内嵌web容器时不管用
        String path = this.getClass().getResource("/").getPath();
        System.out.println("path: " + path);
        BufferedReader bufferedReader = null;
        try {
//            File file = new File(path + "mapper/AreasMapper.xml");
//            bufferedReader = new BufferedReader(new FileReader(file));
            //jar里面的文件读取方式
            InputStream inputStream = new ClassPathResource("mapper/AreasMapper.xml").getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println(bufferedReader.readLine() + "\n" + bufferedReader.readLine() + "\n" + bufferedReader.readLine());
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                }
            }
        }
        result.put("message", "success");
        return result;
    }



    @ResponseBody
    @RequestMapping(value = "/constant/config")
    public Map<String, Object> constantConfig() {
        Map<String, Object> result = new HashMap<>();
        result.put("constant", constantConfig);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/redis/name")
    public void redis(String name) {
        jedisService.setStr("name", name);
    }
}
