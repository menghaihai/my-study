package cn.mfj.implthree.interceptor;

import cn.mfj.implthree.holder.DynamicDataSourceHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author favian.meng on 2021-10-14
 */
public class DsParamsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从http请求中获取到公用请求参数，然后切换到对应的数据库
        String DATA_SOURCE_CODE = "code";
        String dataSourceCode = request.getParameter(DATA_SOURCE_CODE);
        // 不为空则切换数据源
        if (!StringUtils.isEmpty(dataSourceCode)) {
            DynamicDataSourceHolder.setDataSourceCode(dataSourceCode);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
