package cn.mfj.impltwo.annotation;

import cn.mfj.impltwo.common.DataSourceConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mfj on 2021/9/28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DS {

    String value() default DataSourceConstant.MASTER_DATA_SOURCE;
}
