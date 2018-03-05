package com.spring.dao.sys;

import com.spring.domain.sys.SysResource;

public interface SysResourceDao {
    int insert(SysResource record);

    int insertSelective(SysResource record);
}