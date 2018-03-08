package com.spring.boot.study.service;


import com.spring.boot.study.dao.order.AreasMapper;
import com.spring.boot.study.model.Areas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysService {
    @Autowired
    private AreasMapper areasMapper;

    @Transactional
    public Areas getAreaByName(String name) {
        return areasMapper.getByName(name);
    }

    public Areas getAreaById(String id) {
        return areasMapper.getById(id);
    }

}
