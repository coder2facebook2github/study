package com.spring.dao.sys;

import com.spring.domain.sys.SysRole;

public interface SysRoleDao {
    int insert(SysRole record);

    int insertSelective(SysRole record);
}