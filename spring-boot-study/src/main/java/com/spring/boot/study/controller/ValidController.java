package com.spring.boot.study.controller;


import com.spring.boot.study.common.ValidateGroup.group.GroupA;
import com.spring.boot.study.common.ValidateGroup.group.GroupB;
import com.spring.boot.study.model.master.vo.RedisSetVo;
import com.spring.boot.study.model.master.vo.RegisterVo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;

@Validated
@Controller
public class ValidController {

    /**
     * 分组校验
     * 校验 @RequestBody 会抛出 MethodArgumentNotValidException
     */
    @ResponseBody
    @RequestMapping(value = "/valid/a")
    public Map<String, Object> testValid1(@Validated(GroupA.class)@RequestBody RegisterVo user) {
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        return result;
    }

    /**
     * 分组校验
     */
    @ResponseBody
    @RequestMapping(value = "/valid/b")
    public Map<String, Object> testValid2(@Validated(GroupB.class)@RequestBody RegisterVo user) {
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        return result;
    }

    /**
     * 校验全部
     */
    @ResponseBody
    @RequestMapping(value = "/valid/default")
    public Map<String, Object> testValid3(@Validated(Default.class)@RequestBody RegisterVo user) {
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        return result;
    }

    /**
     * 校验全部
     */
    @ResponseBody
    @RequestMapping(value = "/valid/all")
    public Map<String, Object> testValid4(@Validated @RequestBody RegisterVo user) {
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        return result;
    }

    /**
     * 校验全部
     */
    @ResponseBody
    @RequestMapping(value = "/valid/alls")
    public Map<String, Object> testValid5(@Valid @RequestBody RegisterVo user) {
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        return result;
    }

    /**
     * 简单参数校验 会抛出 ConstraintViolationException
     * 需要在controller上加 @Validated注解
     */
    @RequestMapping(value = "/valid/method")
    @ResponseBody
    public Map<String, Object> testValid6(@NotBlank(message = "name:名字不能为空") String name,
                                          @NotBlank(message = "mobile:手机号不能为空") String mobile) {
        Map<String, Object> result = new HashMap<>();
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/valid/1")
    public Map<String, Object> testValid7(@Valid @RequestBody RedisSetVo vo) {
        Map<String, Object> result = new HashMap<>();
        result.put("vo", vo);
        return result;
    }

    /**
     * 校验Request parameter 会抛出 BindException
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/valid/2")
    public Map<String, Object> testValid8(@Valid RedisSetVo vo, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("vo", vo);
        return result;
    }

}
