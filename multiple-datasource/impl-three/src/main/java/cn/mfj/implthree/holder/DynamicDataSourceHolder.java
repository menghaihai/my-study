package cn.mfj.implthree.holder;

import org.springframework.util.StringUtils;

/**
 * 动态数据源线程切换持有者类
 *
 * @author favian.meng on 2021-10-12
 */
public class DynamicDataSourceHolder {

    /**
     * 数据源持有者容器
     */
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    /**
     * 默认数据源code
     */
    private static final String DEFAULT_DATASOURCE_CODE = "DEFAULT";

    /**
     * 手动切换为指定code数据源
     *
     * @param dataSourceCode 数据源code
     */
    public static void setDataSourceCode(String dataSourceCode) {
        holder.set(dataSourceCode);
    }

    /**
     * 获取到当前线程的数据源code字符串
     *
     * @return 数据源code字符串
     */
    public static String getDataSourceCode() {
        String code = holder.get();
        return StringUtils.isEmpty(code) ? DEFAULT_DATASOURCE_CODE : code;
    }

    /**
     * 清除持有者的数据源，释放空间
     */
    public static void clear() {
        holder.remove();
    }
}
