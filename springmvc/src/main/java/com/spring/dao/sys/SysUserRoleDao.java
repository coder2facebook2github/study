package com.spring.dao.sys;

import com.spring.domain.sys.SysUserRole;

public interface SysUserRoleDao {
    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);
}