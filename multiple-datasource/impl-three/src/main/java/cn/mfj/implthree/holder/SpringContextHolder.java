package cn.mfj.implthree.holder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取到Spring bean上下文，从而动态刷新数据源对象
 *
 * @author favian.meng on 2021-10-13
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 获取到Spring上下文对象
     *
     * @return spring上下文
     */
    public static ApplicationContext getApplicationContext() {
        return SpringContextHolder.applicationContext;
    }
}
