package com.spring.boot.study.service;


import com.spring.boot.study.common.Constants;
import com.spring.boot.study.dao.master.sys.SysUserDao;
import com.spring.boot.study.model.master.SysUser;
import com.spring.boot.study.model.master.vo.RegisterVo;
import com.utils.JedisService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private JedisService jedisService;

    public int saveUser(RegisterVo user) {
        user.setSalt(RandomStringUtils.randomAlphabetic(10));
        String password = DigestUtils.md5Hex(user.getPassword() + user.getSalt());
        user.setPassword(password);
        return sysUserDao.insertSelective(user);
    }

    public boolean checkValidateCode(String mobile, String code) {
        try {
//            if(true) {
//                return true;
//            }
            String redisCode = jedisService.getStr(Constants.MESSAGE_CODE + mobile);
            if (StringUtils.isNotBlank(redisCode)) {
                return redisCode.equals(code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public Map<String, String> validateUser(RegisterVo user) {
        Map<String, String> result = new HashMap<>();
        if (user == null) {
            result.put(Constants.MESSAGE, "用户为空");
        } else if (StringUtils.isBlank(user.getMobile())) {
            result.put(Constants.MESSAGE, "手机号为空");
        } else if (StringUtils.isBlank(user.getPassword())) {
            result.put(Constants.MESSAGE, "密码为空");
        } else if (StringUtils.isBlank(user.getEmail())) {
            result.put(Constants.MESSAGE, "邮箱为空");
        } else {
            result.put(Constants.MESSAGE, Constants.SUCCESS);
        }
        return result;
    }

}
