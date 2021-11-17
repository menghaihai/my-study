package cn.mfj.implthree.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mfj on 2021/10/11
 */
@Configuration
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class DynamicDataSourceConfig {


    @Bean
    @Order(1)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Order(2)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Order(3)
    @Primary
    public DataSource dataSource() {
        Map<Object, Object> datasourceMap = new HashMap<>();
        datasourceMap.put("MASTER", masterDataSource());
        datasourceMap.put("SLAVE", slaveDataSource());
        DynamicDataSource dynamicDataSource = new DynamicDataSource(masterDataSource(), datasourceMap);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        dynamicDataSource.setTargetDataSources(datasourceMap);
        return dynamicDataSource;
    }
}
