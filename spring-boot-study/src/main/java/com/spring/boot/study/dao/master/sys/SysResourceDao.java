package com.spring.boot.study.dao.master.sys;

import com.spring.boot.study.model.master.SysResource;

public interface SysResourceDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysResource record);

    int insertSelective(SysResource record);

    SysResource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysResource record);

    int updateByPrimaryKey(SysResource record);
}