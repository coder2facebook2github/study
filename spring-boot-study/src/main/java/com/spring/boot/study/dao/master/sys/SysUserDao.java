package com.spring.boot.study.dao.master.sys;

import com.spring.boot.study.model.master.SysUser;

public interface SysUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    SysUser getByMobile(String mobile);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}