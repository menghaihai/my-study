package cn.mfj.implthree.config;

import cn.mfj.implthree.filter.RepeatReadFilter;
import cn.mfj.implthree.interceptor.DsParamsInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.Collections;

/**
 * Web MVC配置类，本项目中主要用于添加拦截器
 *
 * @author favian.meng on 2021-10-14
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 为Spring MVC添加对应的过滤器
     *
     * @return 过滤器bean配置信息
     */
    @Bean
    public FilterRegistrationBean<Filter> repeatReadFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        RepeatReadFilter repeatReadFilter = new RepeatReadFilter();
        filterRegistrationBean.setFilter(repeatReadFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singleton("/*"));
        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DsParamsInterceptor()).addPathPatterns("/**");
    }


}
