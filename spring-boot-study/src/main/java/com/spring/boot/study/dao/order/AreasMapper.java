package com.spring.boot.study.dao.order;

import com.spring.boot.study.model.Areas;

public interface AreasMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Areas record);

    int insertSelective(Areas record);

    Areas selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Areas record);

    int updateByPrimaryKey(Areas record);

    Areas getByName(String name);

    Areas getById(String id);
}