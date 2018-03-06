package com.spring.boot.study.dao.sys;

import com.spring.boot.study.model.SysResource;

public interface SysResourceMapper {
    int insert(SysResource record);

    int insertSelective(SysResource record);
}