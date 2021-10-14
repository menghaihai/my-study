package cn.mfj.implthree.config;

import cn.mfj.implthree.holder.DynamicDataSourceHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源切换配置
 * <br/>
 * 用于支持运行时添加新的数据源与数据源切换
 * 注意，这个对象最终会交给SpringContext去管理，要想动态添加数据源则要先从Spring上下文获取
 * <br/>
 *
 * @author favian.meng on 2021-10-12
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final Map<Object, Object> backRefreshDsMap;

    /**
     * 初始化数据源
     *
     * @param defaultDataSource 默认数据源
     * @param initDsMap         初始化装填的数据源容器对象
     */
    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> initDsMap) {
        this.backRefreshDsMap = initDsMap;
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(initDsMap);
        super.afterPropertiesSet();
    }

    /**
     * 动态添加数据源
     *
     * @param code       数据源code
     * @param dataSource 数据源对象
     */
    public void addDataSource(String code, DataSource dataSource) {
        backRefreshDsMap.put(code, dataSource);
        // 重新加载多数据源容器
        super.setTargetDataSources(backRefreshDsMap);
        // 重新解析数据源
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSourceCode();
    }
}
