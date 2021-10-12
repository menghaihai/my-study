package cn.mfj.implthree.config;

import cn.mfj.implthree.common.DataSourceUtil;
import cn.mfj.implthree.entity.DataSourceInfo;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 数据源信息配置
 * <br/>
 * 需要排除掉Spring自动注入数据源，使用自己配置的DynamicDataSource,否则数据源会异常
 *
 * @author favian.meng on 2021-10-12
 */
@Configuration
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class DataSourceConfig {

    @Bean
    @Order(1)
    @ConfigurationProperties(prefix = "spring.datasource.default")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Order(2)
    @Primary
    public DataSource dynamicDataSource() {
        // 查询到数据库存储的数据源
        Map<Object, Object> dataSources = DataSourceUtil.getDataSources();
        DynamicDataSource dataSource = new DynamicDataSource(dataSources);
        dataSource.setDefaultTargetDataSource("DEFAULT");

        return null;
    }
}
