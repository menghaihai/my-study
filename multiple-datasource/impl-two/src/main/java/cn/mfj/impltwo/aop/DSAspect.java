package cn.mfj.impltwo.aop;

import cn.mfj.impltwo.annotation.DS;
import cn.mfj.impltwo.holder.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author mfj on 2021/9/28
 */
@Slf4j
@Aspect
@Component
public class DSAspect {

    @Around("@annotation(cn.mfj.impltwo.annotation.DS)")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            DS ds = methodSignature.getMethod().getAnnotation(DS.class);
            String value = ds.value();
            // 切换数据源
            DynamicDataSourceHolder.set(value);
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("DSAspect error!", throwable);
            return null;
        } finally {
            DynamicDataSourceHolder.remove();
        }
    }
}
