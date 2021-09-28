package cn.mfj.implone.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 注入配置，生成主、从数据源并配置到bean上下文
 *
 * @author favian.meng on 2021-09-28
 */
@Configuration
public class DataSourceConfig {

    /**
     * 生成主数据源bean
     *
     * @return master datasource
     */
    @Bean("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 生成从数据源bean
     *
     * @return slave datasource
     */
    @Bean("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }
}
