package com.spring.boot.study.model.vo;

import java.io.Serializable;

public class RedisSetVo implements Serializable {

    private static final long serialVersionUID = 6796043823084894200L;
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RedisSetVo{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
