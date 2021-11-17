package cn.mfj.implthree.aop;

import cn.mfj.implthree.annotation.Ds;
import cn.mfj.implthree.common.Ds;
import cn.mfj.implthree.holder.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author favian.meng on 2021-10-13
 */
@Slf4j
@Aspect
@Component
public class DsAspect {

    @Pointcut("@annotation(cn.mfj.implthree.annotation.Ds)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            String value = methodSignature.getMethod().getAnnotation(Ds.class).value();
            DynamicDataSourceHolder.setDataSourceCode(value);
            return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        } catch (Throwable throwable) {
            log.error("Ds annotation aspect error", throwable);
            return null;
        } finally {
            DynamicDataSourceHolder.clear();
        }
    }
}
