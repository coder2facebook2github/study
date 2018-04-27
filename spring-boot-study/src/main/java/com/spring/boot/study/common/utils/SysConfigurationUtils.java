package com.spring.boot.study.common.utils;


import com.google.common.base.Splitter;
import com.spring.boot.study.common.Constants;
import com.utils.JedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SysConfigurationUtils {
    @Autowired
    private JedisService jedisService;

    public String getString(String key) {
        return jedisService.hget(Constants.SYS_CONFIGURATIONS, key);
    }

    public List<String> getListString(String key) {
        String value = jedisService.hget(Constants.SYS_CONFIGURATIONS, key);
        List<String> result = null;
        if(StringUtils.isNotBlank(value)) {
            result = Splitter.on(",").splitToList(value);
        }
        return result;
    }
}
