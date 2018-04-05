package com.spring.boot.study.controller;


import com.spring.boot.study.model.master.Areas;
import com.spring.boot.study.model.master.vo.RedisSetVo;
import com.utils.JedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RedisController {

    @Autowired
    private JedisService jedisService;

    private static final String AREAS_LIST = "AREAS_LIST";


    @ResponseBody
    @RequestMapping(value = "/redis/set/areas/list")
    public Map<String, Object> redisSet(@RequestBody List<Areas> list) {
        Map<String, Object> result = new HashMap<>();
        for (Areas areas : list) {
            jedisService.rpush(AREAS_LIST, areas);
        }
        List<Areas> areasList = jedisService.lrange(AREAS_LIST, 0, -1);
        result.put("areasList", areasList);
        result.put("message", "success");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/redis/set/string/{key}/{value}", method = RequestMethod.POST)
    public Map<String, Object> redisSet(@PathVariable String key, @PathVariable String value) {
        Map<String, Object> result = new HashMap<>();
        jedisService.setStr(key, value);
        result.put(key, value);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/redis/set/body", method = RequestMethod.POST)
    public Map<String, Object> redisSet1(@RequestBody RedisSetVo vo) {
        Map<String, Object> result = new HashMap<>();
        jedisService.setStr(vo.getKey(), vo.getValue());
        result.put(vo.getKey(), vo.getValue());
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/redis/get/string/{key}")
    public Map<String, Object> redisGet(@PathVariable String key) {
        Map<String, Object> result = new HashMap<>();
        String value = jedisService.getStr(key);
        result.put(key, value);
        result.put("message", "success");
        return result;
    }


}
