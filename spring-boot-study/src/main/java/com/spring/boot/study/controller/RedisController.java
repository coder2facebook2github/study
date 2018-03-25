package com.spring.boot.study.controller;


import com.spring.boot.study.model.Areas;
import com.utils.JedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


}
