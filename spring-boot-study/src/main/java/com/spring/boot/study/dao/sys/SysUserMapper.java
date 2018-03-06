package com.spring.boot.study.dao.sys;

import com.spring.boot.study.model.SysUser;

public interface SysUserMapper {
    int insert(SysUser record);

    int insertSelective(SysUser record);
}