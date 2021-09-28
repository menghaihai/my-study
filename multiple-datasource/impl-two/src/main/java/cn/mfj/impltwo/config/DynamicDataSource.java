package cn.mfj.impltwo.config;

import cn.mfj.impltwo.common.DataSourceConstant;
import cn.mfj.impltwo.holder.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源配置
 *
 * @author favian.meng on 2021-09-28
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = DynamicDataSourceHolder.get();
        log.info("获取到的数据源为:{}", dataSource);
        return dataSource;
    }
}
