package com.spring.boot.study.common.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.spring.boot.study.dao.master", sqlSessionTemplateRef = "sqlSessionTemplateMaster")
public class DatasourceMasterConfig {

    @Primary
    @Bean("datasourceMaster")
    @ConfigurationProperties("spring.datasource.master")
    public DataSource dataSourceMaster(){
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean("transactionManagerMaster")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("datasourceMaster") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean("sqlSessionFactoryMaster")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("datasourceMaster") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/master/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean("sqlSessionTemplateMaster")
    public SqlSessionTemplate setSqlSessionFactory(@Qualifier("sqlSessionFactoryMaster") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }



}
