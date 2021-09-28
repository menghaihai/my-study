package cn.mfj.implone.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author favian.meng on 2021-09-28
 */
@Configuration
//@MapperScan(basePackages = "cn.mfj.implone.mapper.master", sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterMybatisConfig {

    @Bean("masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        // 使用MybatisPlus包下的SqlSessionFactoryBean，用于兼容MybatisPlus
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        // 指定读取的mapper.xml文件位置
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = patternResolver.getResources("classpath*:/mapper/master/*.xml");
        mybatisSqlSessionFactoryBean.setMapperLocations(resources);
        mybatisSqlSessionFactoryBean.setTypeAliasesPackage("cn.mfj.implone.entity.master");
        return mybatisSqlSessionFactoryBean.getObject();
    }
}
