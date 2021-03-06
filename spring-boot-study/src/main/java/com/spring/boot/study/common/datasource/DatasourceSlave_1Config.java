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
@MapperScan(basePackages = "com.spring.boot.study.dao.slave_1",
        sqlSessionTemplateRef = "sqlSessionTemplateSlave_1",
        sqlSessionFactoryRef = "sqlSessionFactorySlave_1")
public class DatasourceSlave_1Config {

    @Bean("datasourceSlave_1")
    @ConfigurationProperties("spring.datasource.slave1")
    public DataSource setDatasource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("transactionManagerSlave_1")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("datasourceSlave_1") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("mybatisConfigurationSlave_1")
    @ConfigurationProperties("mybatis-slave1.config")
    public org.apache.ibatis.session.Configuration getMybatisConfig() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean("sqlSessionFactorySlave_1")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("datasourceSlave_1") DataSource dataSource,
                                                  @Qualifier("mybatisConfigurationSlave_1") org.apache.ibatis.session.Configuration mybatisConfig) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfiguration(mybatisConfig);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/slave_1/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean("sqlSessionTemplateSlave_1")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("sqlSessionFactorySlave_1") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
