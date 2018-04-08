package com.spring.boot.study.service;


import com.spring.boot.study.dao.master.order.AreasDao;
import com.spring.boot.study.dao.master.sys.SysUserDao;
import com.spring.boot.study.dao.slave_1.sys.SiteInfoDao;
import com.spring.boot.study.dao.slave_1.sys.SysUserSlave1Dao;
import com.spring.boot.study.model.SiteInfo;
import com.spring.boot.study.model.master.Areas;
import com.spring.boot.study.model.master.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class SysService {
    @Autowired
    private AreasDao areasDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserSlave1Dao sysUserSlave1Dao;
    @Autowired
    private SiteInfoDao siteInfoDao;
    @Autowired
    private ExecutorService executorService;

    @Transactional
    public Areas getAreaByName(String name) {
        return areasDao.getByName(name);
    }

    public Areas getAreaById(String id) {
        return areasDao.getById(id);
    }

    public Map<String, Object> getUserByMobile(String mobile) {
        SysUser masterUser = sysUserDao.getByMobile(mobile);
        SysUser slaveUser = sysUserSlave1Dao.getByMobile(mobile);
        Map<String, Object> users = new HashMap<>();
        users.put("masterUser", masterUser);
        users.put("slaveUser", slaveUser);
        return users;
    }

    public SiteInfo getSiteInfoById(long id) {
        return siteInfoDao.selectByPrimaryKey(id);
    }

    @Async("taskExecutor")
    public void doTask() {
        long start = System.currentTimeMillis();
        try {
            Future<String> task1 = executorService.taskOne();
            Future<String> task2 = executorService.taskTwo();
            Future<String> task3 = executorService.taskThree();
            while (true) {
                if(task1.isDone() && task2.isDone() && task3.isDone()) {
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.printf("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }

}
