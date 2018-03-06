package com.spring.boot.study.dao.sys;

import com.spring.boot.study.model.SysRole;

public interface SysRoleMapper {
    int insert(SysRole record);

    int insertSelective(SysRole record);
}