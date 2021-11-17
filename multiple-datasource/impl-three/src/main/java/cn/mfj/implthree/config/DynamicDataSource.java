package cn.mfj.implthree.config;

import cn.mfj.implthree.holder.DynamicDataSourceHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

/**
 * @author mfj on 2021/10/11
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceKey = DynamicDataSourceHolder.getDataSourceKey();
        return StringUtils.isEmpty(dataSourceKey) ? "MASTER" : dataSourceKey;
    }
}
