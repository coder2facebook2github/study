package com.spring.boot.study.dao.slave_1.sys;

import com.spring.boot.study.model.SiteInfo;

public interface SiteInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(SiteInfo record);

    int insertSelective(SiteInfo record);

    SiteInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SiteInfo record);

    int updateByPrimaryKey(SiteInfo record);
}