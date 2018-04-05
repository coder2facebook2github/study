package com.spring.boot.study.service;


import com.spring.boot.study.dao.master.order.AreasDao;
import com.spring.boot.study.dao.master.sys.SysUserDao;
import com.spring.boot.study.dao.slave_1.sys.SysUserSlave1Dao;
import com.spring.boot.study.model.master.Areas;
import com.spring.boot.study.model.master.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysService {
    @Autowired
    private AreasDao areasDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserSlave1Dao sysUserSlave1Dao;

    @Transactional
    public Areas getAreaByName(String name) {
        return areasDao.getByName(name);
    }

    public Areas getAreaById(String id) {
        return areasDao.getById(id);
    }

    public Map<String, Object> getByMobile(String mobile) {
        SysUser masterUser = sysUserDao.getByMobile(mobile);
        SysUser slaveUser = sysUserSlave1Dao.getByMobile(mobile);
        Map<String, Object> users = new HashMap<>();
        users.put("masterUser", masterUser);
        users.put("slaveUser", slaveUser);
        return users;
    }

}
