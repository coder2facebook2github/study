package com.spring.dao.sys;

import com.spring.domain.sys.SysRoleResource;

public interface SysRoleResourceDao {
    int insert(SysRoleResource record);

    int insertSelective(SysRoleResource record);
}