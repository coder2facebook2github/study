package com.spring.dao.sys;

import com.spring.domain.sys.Areas;

public interface AreasDao {
    int insert(Areas record);

    int insertSelective(Areas record);
}