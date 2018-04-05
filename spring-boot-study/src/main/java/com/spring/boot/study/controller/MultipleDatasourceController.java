package com.spring.boot.study.controller;

import com.spring.boot.study.model.SiteInfo;
import com.spring.boot.study.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Controller
public class MultipleDatasourceController {

    @Autowired
    private SysService sysService;

    @ResponseBody
    @RequestMapping(value = "/multiple/user")
    public Map<String, Object> getObject(String mobile) {
        return sysService.getUserByMobile(mobile);
    }

    @ResponseBody
    @RequestMapping(value = "/site/info/{id}")
    public SiteInfo getSiteInfo(@PathVariable("id") @NotBlank(message = "id:id不能为空") Long id) {
        return sysService.getSiteInfoById(id);
    }

}
