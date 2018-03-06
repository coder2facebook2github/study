package com.spring.boot.study.dao.sys;

import com.spring.boot.study.model.SysRoleResource;

public interface SysRoleResourceMapper {
    int insert(SysRoleResource record);

    int insertSelective(SysRoleResource record);
}