package cn.itbeien.multi.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Configuration
@MapperScan(basePackages = "cn.itbeien.multi.mapper.testtwo", sqlSessionTemplateRef  = "testTwoSqlSessionTemplate")
public class DataSourceTwoConfig {

    @Bean(name = "testTwoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.testtwo")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "testTwoSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("testTwoDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "testTwoTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("testTwoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "testTwoSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("testTwoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
