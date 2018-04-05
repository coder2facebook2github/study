package com.spring.boot.study.dao.slave_1.sys;

import com.spring.boot.study.model.master.SysUser;

public interface SysUserSlave1Dao {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    SysUser getByMobile(String mobile);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}