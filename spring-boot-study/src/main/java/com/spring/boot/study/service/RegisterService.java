package com.spring.boot.study.service;


import com.spring.boot.study.model.SysUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    public int saveUser(SysUser user) {
//        user.setSalt(RandomStringUtils.randomAlphabetic());
        return 0;
    }
}
