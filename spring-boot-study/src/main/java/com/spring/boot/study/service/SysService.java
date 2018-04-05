package com.spring.boot.study.service;


import com.spring.boot.study.dao.master.order.AreasDao;
import com.spring.boot.study.model.master.Areas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysService {
    @Autowired
    private AreasDao areasDao;

    @Transactional
    public Areas getAreaByName(String name) {
        return areasDao.getByName(name);
    }

    public Areas getAreaById(String id) {
        return areasDao.getById(id);
    }

}
