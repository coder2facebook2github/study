package com.spring.boot.study.dao.master.sys;

import com.spring.boot.study.model.master.SysConfigurations;

import java.util.List;

public interface SysConfigurationsDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysConfigurations record);

    int insertSelective(SysConfigurations record);

    SysConfigurations selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysConfigurations record);

    List<SysConfigurations> getAllUsedConfigurations();

    int updateByPrimaryKey(SysConfigurations record);
}