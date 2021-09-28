package cn.mfj.impltwo.holder;

import cn.mfj.impltwo.common.DataSourceConstant;

/**
 * @author favian.meng on 2021-09-28
 */
public class DynamicDataSourceHolder {

    /**
     * 存储DataSource key的上下文，一般绑定的为每个请求的线程，从而做到单线程的数据源隔离
     */
    private static final ThreadLocal<String> DATASOURCE_CONTEXT_KEYS_HOLDER = new ThreadLocal<>();

    /**
     * set数据源的key，绑定线程的上下文数据源
     *
     * @param dataSourceKey 数据源key
     */
    public static void set(String dataSourceKey) {
        DATASOURCE_CONTEXT_KEYS_HOLDER.set(dataSourceKey);
    }

    /**
     * get到线程上线文中的数据源key
     *
     * @return 数据源key
     */
    public static String get() {
        String dataSourceKey = DATASOURCE_CONTEXT_KEYS_HOLDER.get();
        return dataSourceKey == null ? DataSourceConstant.MASTER_DATA_SOURCE : dataSourceKey;
    }

    /**
     * 移除掉上下文中数据源的key
     */
    public static void remove() {
        DATASOURCE_CONTEXT_KEYS_HOLDER.remove();
    }
}
