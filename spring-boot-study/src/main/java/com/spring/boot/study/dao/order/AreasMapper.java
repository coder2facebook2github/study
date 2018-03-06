package com.spring.boot.study.dao.order;

import com.spring.boot.study.model.Areas;

public interface AreasMapper {
    int insert(Areas record);

    int insertSelective(Areas record);

    Areas getByName(String name);
}