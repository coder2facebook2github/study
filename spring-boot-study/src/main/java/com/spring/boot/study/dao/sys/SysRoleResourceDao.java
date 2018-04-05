package com.spring.boot.study.dao.sys;

import com.spring.boot.study.model.SysRoleResource;

public interface SysRoleResourceDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleResource record);

    int insertSelective(SysRoleResource record);

    SysRoleResource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleResource record);

    int updateByPrimaryKey(SysRoleResource record);
}