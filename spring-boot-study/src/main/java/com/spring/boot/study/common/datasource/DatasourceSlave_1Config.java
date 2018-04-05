package com.spring.boot.study.common.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.spring.boot.study.dao.slave_1",
        sqlSessionFactoryRef = "sqlSessionFactorySlave_1",
        sqlSessionTemplateRef = "sqlSessionTemplateSlave_1")
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



    @Bean("sqlSessionFactorySlave_1")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("datasourceSlave_1") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration mybatisConfiguration = new org.apache.ibatis.session.Configuration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        mybatisConfiguration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        mybatisConfiguration.setUseGeneratedKeys(false);
        sqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/slave_1/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean("sqlSessionTemplateSlave_1")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("sqlSessionFactorySlave_1") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
