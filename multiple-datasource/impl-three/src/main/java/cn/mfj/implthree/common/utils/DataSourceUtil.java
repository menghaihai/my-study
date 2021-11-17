package cn.mfj.implthree.common.utils;

import cn.mfj.implthree.config.DynamicDataSource;
import cn.mfj.implthree.entity.DataSourceInfo;
import cn.mfj.implthree.holder.SpringContextHolder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author favian.meng on 2021-10-12
 */
public class DataSourceUtil {

    private static final String driverClassName;

    private static final String url;

    private static final String username;

    private static final String password;

    private static final DataSource DEFAULT_DATASOURCE;

    static {
        driverClassName = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/my_study?characterEncoding=utf-8";
        username = "root";
        password = "root";
        // 初始化数据源
        DEFAULT_DATASOURCE = DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    /**
     * 获取到数据源map容器
     *
     * @return 数据源map容器
     */
    public static Map<Object, Object> getDataSources() {
        List<DataSourceInfo> dataSourceList = list();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceList.forEach(dataSourceInfo -> {
            DataSource dataSource = conversionDataSource(dataSourceInfo);
            dataSourceMap.put(dataSourceInfo.getCode(), dataSource);
        });
        return dataSourceMap;
    }

    /**
     * 查询所有的数据源
     *
     * @return 数据源列表信息
     */
    public static List<DataSourceInfo> list() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DEFAULT_DATASOURCE);
        String sql = "SELECT * FROM t_datasource_info";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DataSourceInfo.class));
    }

    /**
     * 将DataSourceInfo对象转换为HikariDataSource对象
     *
     * @param dataSourceInfo 数据源相关信息实体类
     * @return sql数据源对象
     */
    public static DataSource conversionDataSource(DataSourceInfo dataSourceInfo) {
        return DataSourceBuilder.create()
                .driverClassName(dataSourceInfo.getDriverClassName())
                .url(dataSourceInfo.getUrl())
                .username(dataSourceInfo.getUsername())
                .password(dataSourceInfo.getPassword())
                .build();
    }

    /**
     * 获取默认数据源
     *
     * @return 默认数据源对象
     */
    public static DataSource getDefaultDataSource() {
        return DEFAULT_DATASOURCE;
    }

    /**
     * 添加数据源信息
     *
     * @param dataSourceInfo 数据源对象信息
     */
    public static void addDataSource(DataSourceInfo dataSourceInfo) {
        DynamicDataSource dynamicDataSource = SpringContextHolder.getApplicationContext().getBean(DynamicDataSource.class);
        dynamicDataSource.addDataSource(dataSourceInfo.getCode(), conversionDataSource(dataSourceInfo));

    }
}
