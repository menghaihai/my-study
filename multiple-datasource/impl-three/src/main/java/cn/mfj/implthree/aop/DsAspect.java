package cn.mfj.implthree.aop;

import cn.mfj.implthree.common.Ds;
import cn.mfj.implthree.holder.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author mfj on 2021/10/11
 */
@Slf4j
@Aspect
public class DsAspect {

    @Pointcut("@annotation(cn.mfj.implthree.common.Ds)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            Ds dsAnnotation = methodSignature.getMethod().getAnnotation(Ds.class);
            String datasourceKey = dsAnnotation.value();
            DynamicDataSourceHolder.setDataSourceKey(datasourceKey);
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            log.error("DsDatasource annotation aspect error!", throwable);
            return null;
        }finally {
            DynamicDataSourceHolder.clear();
        }
    }
}
