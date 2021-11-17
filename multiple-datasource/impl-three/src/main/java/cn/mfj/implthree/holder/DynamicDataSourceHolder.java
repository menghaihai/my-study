package cn.mfj.implthree.holder;

import org.springframework.util.StringUtils;

/**
 * @author mfj on 2021/10/11
 */
public class DynamicDataSourceHolder {

    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setDataSourceKey(String dataSourceKey) {
        threadLocal.set(dataSourceKey);
    }

    public static String getDataSourceKey() {
        String datasourceKey = threadLocal.get();
        return StringUtils.isEmpty(datasourceKey) ? "MASTER" : datasourceKey;
    }

    public static void clear() {
        threadLocal.remove();
    }
}
