package com.spring.boot.study.dao.sys;

import com.spring.boot.study.model.SysConfigurations;

import java.util.List;

public interface SysConfigurationsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysConfigurations record);

    int insertSelective(SysConfigurations record);

    SysConfigurations selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysConfigurations record);

    List<SysConfigurations> getAllUsedConfigurations();

    int updateByPrimaryKey(SysConfigurations record);
}