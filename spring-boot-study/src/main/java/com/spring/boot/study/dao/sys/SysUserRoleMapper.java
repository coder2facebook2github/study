package com.spring.boot.study.dao.sys;

import com.spring.boot.study.model.SysUserRole;

public interface SysUserRoleMapper {
    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);
}