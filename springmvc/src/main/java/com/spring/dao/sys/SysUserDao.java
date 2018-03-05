package com.spring.dao.sys;

import com.spring.domain.sys.SysUser;

import java.util.List;

public interface SysUserDao {
    int insert(SysUser record);

    int insertSelective(SysUser record);

    List<SysUser> queryAllUsers();

    SysUser getById(Long userId);
}