package com.spring.boot.study.dao.master.sys;

import com.spring.boot.study.model.master.SysUserRole;

public interface SysUserRoleDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
}